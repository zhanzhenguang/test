package com.it.zzg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * MVC配置
 *
 * @author admin
 * @email admin
 * @date 2017-04-20 22:30
 */
@Configuration
public class RestMvcConfig extends WebMvcConfigurerAdapter {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedHeaders("*")
        .allowedOrigins("*")
        .allowedMethods("GET", "POST")
        .allowCredentials(true)
        .maxAge(3600);
    }
    
}