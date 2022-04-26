package io.github.wesleyosantos91.configuration;

import io.jaegertracing.internal.MDCScopeManager;
import io.opentracing.contrib.java.spring.jaeger.starter.TracerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JaegerConfig {

    @Bean
    public TracerBuilderCustomizer mdcBuilderCustomizer() {
        return builder -> builder.withScopeManager(new MDCScopeManager.Builder().build());
    }
}
