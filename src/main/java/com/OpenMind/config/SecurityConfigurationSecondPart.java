package com.OpenMind.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurationSecondPart extends GlobalMethodSecurityConfiguration {

private final OpenMindMethodSecurityExpressionHandler openMindMethodSecurityExpressionHandler;

    public SecurityConfigurationSecondPart(OpenMindMethodSecurityExpressionHandler openMindMethodSecurityExpressionHandler) {
        this.openMindMethodSecurityExpressionHandler = openMindMethodSecurityExpressionHandler;
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return openMindMethodSecurityExpressionHandler;
    }

}
