package com.rosetta.app.interceptor.configuration;

import com.rosetta.app.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer
{
    private final AuthorizationInterceptor authorizationInterceptor;

    public InterceptorConfiguration(AuthorizationInterceptor authorizationInterceptor)
    {
        this.authorizationInterceptor = authorizationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/api/**");
    }
}
