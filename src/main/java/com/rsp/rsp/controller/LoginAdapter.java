package com.rsp.rsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * 登录拦截
 * @author sjb
 */
@Configuration
public class LoginAdapter implements WebMvcConfigurer {
    @Autowired
    LoginInterceptor loginInterceptor;

    /**
     * 不需要登录拦截的url:登录注册和验证码
     */
    final String[] notLoginInterceptPaths = {"/index","/css/**","/js/**","/images/**","/plugins/**"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(notLoginInterceptPaths);
    }
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }
}
