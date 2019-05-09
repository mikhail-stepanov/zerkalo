package com.tochka.common.configurations;

import com.tochka.common.interceptors.MetricInterceptor;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MicroserviceMvcConfigurer implements WebMvcConfigurer {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    MeterRegistry meterRegistry;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MetricInterceptor(meterRegistry, applicationName));
    }
}
