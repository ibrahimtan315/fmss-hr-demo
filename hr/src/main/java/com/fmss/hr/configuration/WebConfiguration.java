package com.fmss.hr.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://10.134.3.16:3000", "http://10.134.3.16")
                .allowedMethods("POST", "DELETE", "PUT", "GET")
                .allowedHeaders("Content-Type", "Authorization", "header3") //TODO header gönderirken buraya eklemek zorundasın
                .allowCredentials(false).maxAge(3600);
    }
}
