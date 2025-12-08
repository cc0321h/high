package org.explore.high.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.explore.high.interceptor.JwtTokenAdminInterceptor;
import org.explore.high.interceptor.JwtTokenUserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Slf4j
@Configuration
@AllArgsConstructor
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;
    /**
     * 注册自定义拦截器
     * @param registry 拦截器注册类
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/employee/login");

        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/api/users/**")
                .excludePathPatterns("/api/users/login", "/api/users/register");
    }
}
