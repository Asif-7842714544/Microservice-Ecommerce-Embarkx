package com.ecommerce.order.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignTracingConfig {

    private final Tracer tracer;
    private final Propagator propagator;

    public FeignTracingConfig(Tracer tracer, Propagator propagator) {
        this.tracer = tracer;
        this.propagator = propagator;
    }

    @Bean
    public RequestInterceptor tracingInterceptor() {
        return template -> {
            var currentSpan = tracer.currentSpan();
            if (currentSpan == null) {
                return;
            }

            propagator.inject(
                    currentSpan.context(),
                    template,
                    (req, key, value) -> req.header(key, value)
            );
        };
    }

//    @Bean
//    public SpanExportingPredicate skipPrometheus() {
//        return span -> {
//            String name = span.getName();
//            if (name == null) return true;
//
//            // block prometheus endpoint from being sent to Zipkin
//            return !name.contains("/prometheus");
//        };
//    }
//

}