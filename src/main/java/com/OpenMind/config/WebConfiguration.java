package com.OpenMind.config;

import com.OpenMind.web.interceptor.StatInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final StatInterceptor statInterceptor;

    public WebConfiguration(StatInterceptor statInterceptor) {
        this.statInterceptor = statInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(statInterceptor);
    }
}
