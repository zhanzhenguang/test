package com.it.zzg.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//配置类加入自定义拦截器
//@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport{

  @Override
  public void addInterceptors(InterceptorRegistry registry){
      registry.addInterceptor(new CorsInterceptor()).addPathPatterns("/**");
  }

}
