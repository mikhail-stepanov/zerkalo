package social.tochka.common.interfaces;

public interface IMetricsService {

    void sendMetric(String appName, String metricType, String metricJson);
}
