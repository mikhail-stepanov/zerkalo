package zerkalo.common.interfaces;

public interface IMetricsService {

    void sendMetric(String appName, String metricType, String metricJson);
}
