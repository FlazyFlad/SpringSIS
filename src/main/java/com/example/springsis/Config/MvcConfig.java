package com.example.springsis.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * smth.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

  /**
   * smth.
   */
  @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
  }
}
