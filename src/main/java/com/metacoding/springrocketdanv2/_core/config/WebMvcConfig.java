package com.metacoding.springrocketdanv2._core.config;

import com.metacoding.springrocketdanv2._core.interceptor.CompanyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { // 이미지 달라고 요청하면 발동하는 핸들러
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry.addResourceHandler("/upload/**")// pattern 주소에 요청이 오면 발동
                .addResourceLocations("file:./upload/") // file: <- 파일 프로토콜, "./" <- 프로젝트 경로
                .setCachePeriod(60 * 60) // 초 단위 => 한시간
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

    @Deprecated
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new CompanyInterceptor())
                .addPathPatterns("/company/update-form")
                .addPathPatterns("/company/update")
                .addPathPatterns("/job/**")
                .excludePathPatterns("/job")
                .excludePathPatterns("/job/{id:\\d+}")
                .excludePathPatterns("/job/{id:\\d+}/bookmark");
    }
}
