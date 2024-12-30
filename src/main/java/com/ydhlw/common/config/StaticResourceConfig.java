package com.ydhlw.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射 "/uploads/**" 到文件系统中的实际路径
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/www/wwwroot/javaback-ydhlw/uploads/")
                .setCachePeriod(3600); // 设置缓存时间为 3600 秒
    }
}

