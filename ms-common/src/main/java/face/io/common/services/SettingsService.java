package face.io.common.services;

import face.io.common.models.SettingsInformation;
import face.io.common.annotations.SettingsEndpoint;
import face.io.common.annotations.SettingsMethod;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class SettingsService {

    private final List<SettingsInformation> settingsInformations = new ArrayList<>();

    @PostConstruct
    private void init(){
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);
        scanner.addIncludeFilter(new AnnotationTypeFilter(SettingsEndpoint.class));
        for (BeanDefinition bd : scanner.findCandidateComponents("ru.ecom.np")){

            try {

                Class<?> annotatedClass = Class.forName(bd.getBeanClassName());

                SettingsEndpoint annotation = annotatedClass.getAnnotation(SettingsEndpoint.class);
                if (annotation == null)
                    continue;

                Map<String,String> methods = new HashMap<>();

                Arrays.stream(annotatedClass.getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(SettingsMethod.class) && method.isAnnotationPresent(RequestMapping.class))
                        .forEach(method ->{
                            SettingsMethod settingsMethod = method.getAnnotation(SettingsMethod.class);
                            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                            methods.put(settingsMethod.methodType().toString(), requestMapping.value()[0]);
                        });

                settingsInformations.add(SettingsInformation.builder()
                        .object(annotation.objectType())
                        .property(annotation.propertyType())
                        .methods(methods)
                        .build());

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public SettingsInformation[] settingsInformations(){
        return settingsInformations.toArray(new SettingsInformation[0]);
    }
}
