package social.tochka.ms.client.common.services;


import social.tochka.ms.client.exceptions.MicroServiceException;
import social.tochka.ms.client.exceptions.MsBadRequestException;
import social.tochka.ms.client.exceptions.MsInternalErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;

public class BaseMicroservice {

    private String serviceName;
    protected final RestTemplate restTemplate;

    @Value("${ms.retry.count:4}")
    private int retry;

    @Value("${ms.retry.time.sec:2}")
    private int sleepTime;

    protected BaseMicroservice(String serviceName, RestTemplate restTemplate) {

        this.serviceName = serviceName;
        this.restTemplate = restTemplate;

        restTemplate.getInterceptors().add((request, body, execution) -> {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes instanceof ServletRequestAttributes) {
                HttpServletRequest currentRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
                Collections.list(currentRequest.getHeaderNames()).stream()
                        .filter(header -> header.startsWith("x-ecom-"))
                        .forEach(header -> {
                            request.getHeaders().set(header, currentRequest.getHeader(header));
                        });
            }
            return execution.execute(request, body);
        });
    }

    protected String buildUrl(String method) {
        return String.format("http://%s%s", serviceName, method);
    }

    protected <T> T retry(Callable<T> callable) throws MicroServiceException {
        int attempt = 0;
        while (attempt++ < retry) {
            try {
                try {
                    return callable.call();
                } catch (Exception ex) {
                    if (ex.getMessage().contains("40")) {
                        throw new MsBadRequestException(ex.getMessage());
                    }
                    if (ex.getMessage().contains("50") || ex.getMessage().contains("No instances available")) {
                        Thread.sleep(sleepTime * 1000);
                        if (attempt == retry)
                            throw new MsInternalErrorException(ex.getMessage());
                    }
                }
            } catch (InterruptedException e) {
            }
        }
        throw new MsInternalErrorException("Timeout");
    }
}
