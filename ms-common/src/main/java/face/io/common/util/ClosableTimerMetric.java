package face.io.common.util;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

import java.io.IOException;

public class ClosableTimerMetric  implements AutoCloseable {

    private final Timer.Sample timer;
    private final String metricName;
    private final MeterRegistry meterRegistry;

    public ClosableTimerMetric(MeterRegistry meterRegistry, String metricName){
        this.meterRegistry = meterRegistry;
        this.metricName = metricName;
        this.timer = Timer.start(meterRegistry);
    }

    @Override
    public void close() throws IOException {
        timer.stop(meterRegistry.timer(metricName));
    }
}
