package face.io.common.failover;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.health.model.HealthService;
import com.ecwid.consul.v1.kv.model.GetValue;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Base64Utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConsulFailoverStarter {

    private String applicationName;
    private Class<?> applicationClass;
    private ConfigurableApplicationContext context;

    private final Thread monitorThread;
    private final Thread startThread;
    private final Thread stopThread;

    private final ConsulClient consulClient;
    private final DistirbutedConsulLock distrDistirbutedLock;

    final Lock lock = new ReentrantLock();
    final Condition shouldStart  = lock.newCondition();
    final Condition shouldStop = lock.newCondition();

    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicBoolean shutdown = new AtomicBoolean(false);

    private ConsulFailoverStarter(Class<?> applicationClass) {
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdownHook));

        this.applicationClass = applicationClass;
        this.applicationName = parseBootstrapProperties();

        monitorThread = new Thread(this::monitorThread);
        startThread = new Thread(this::startThread);
        stopThread = new Thread(this::stopThread);

        consulClient = new ConsulClient();
        distrDistirbutedLock = new DistirbutedConsulLock(consulClient, applicationClass.getName());
    }

    public static void start(Class<?> clazz) {
        ConsulFailoverStarter failoverMonitor = new ConsulFailoverStarter(clazz);
        failoverMonitor.start();
    }

    public void start() {
        startThread.start();
        stopThread.start();
        monitorThread.start();
    }

    private String parseBootstrapProperties(){
        try{
            String propsName = "bootstrap.properties";
            InputStream propsStream = applicationClass.getClassLoader().getResourceAsStream(propsName);
            if (propsStream != null) {
                Properties props = new Properties();
                props.load(propsStream);
                return props.getProperty("spring.application.name");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "";
    }

    private String getLeaderFromProperties(String properties){
        try{
            Properties props = new Properties();
            props.load(new ByteArrayInputStream(Base64Utils.decodeFromString(properties)));
            return props.getOrDefault("leader.node", "").toString();
        }catch (Exception ex){

        }
        return "";
    }

    private void monitorThread() {
        while (!shutdown.get()) {
            try {
                Thread.sleep(500);

                lock.lock();
                //ставим лок
                if (!distrDistirbutedLock.lock(true)){
                    continue;
                }

                String key = String.format("/configuration/%s/data", applicationName);
                Response<GetValue> kvValue = consulClient.getKVValue(key, QueryParams.DEFAULT);
                String leaderNodeName = getLeaderFromProperties(kvValue.getValue().getValue());


                //определяем название нашей ноды
                String myNodeName = consulClient.getAgentSelf().getValue().getConfig().getNodeName();

                //получаем живые сервисы
                List<HealthService> healthServices = consulClient.getHealthServices("ms-files", true, QueryParams.DEFAULT).getValue();

                //определяем жив ли лидер
                boolean leaderExists = healthServices.stream().anyMatch(hs -> hs.getNode().getNode().equals(leaderNodeName));

                //если мы должны быть лидером
                if (leaderNodeName.equalsIgnoreCase(myNodeName)){

                    //если контекст запущен
                    if (running.get())
                        continue;

                    //если контекст не запущен
                    shouldStart.signal();
                    continue;
                }

                //если мы не должны быть лидером
                if (!leaderNodeName.equalsIgnoreCase(myNodeName)){

                    //если мы все же запущены - останавливаемся
                    if (leaderExists && running.get()){
                        shouldStop.signal();
                    }

                    //если нет ни одного живого сервиса - запускаемся
                    if (healthServices.isEmpty() && !running.get()){
                        shouldStart.signal();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                distrDistirbutedLock.unlock();
                lock.unlock();
            }
        }
    }

    private void startThread() {
        while (!shutdown.get()) {
            try {
                lock.lock();

                //ждем сигнал на старт
                shouldStart.await();

                //если процесс запущен - запускать нечего
                if (running.get()){
                    continue;
                }

                //устанавливаем флаг
                running.set(true);

                context = SpringApplication.run(applicationClass);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    private void stopThread() {
        while (!shutdown.get()) {
            try {
                lock.lock();

                //ждем сигнал на остановку
                shouldStop.await();

                //если процесс не запущен - стопить нечего
                if (!running.get())
                    continue;

                //устанавливаем флаг
                running.set(false);

                //закрываем контекст приложения
                context.close();
                context = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
                lock.unlock();
            }
        }

        if (running.get() && context != null){
            context.close();
            context = null;
        }
    }

    private void shutdownHook() {
        shutdown.set(true);
    }
}
