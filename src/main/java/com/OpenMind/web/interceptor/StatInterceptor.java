package com.OpenMind.web.interceptor;

import com.OpenMind.serveces.StatService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class StatInterceptor implements HandlerInterceptor {

    private final StatService statService;

    public StatInterceptor(StatService statService) {
        this.statService = statService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        statService.onRequest();
        return true;
    }
}
