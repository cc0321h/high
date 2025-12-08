package org.explore.high.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    /**
     * 配置BCryptPasswordEncoder用于密码加密
     * @return BCryptPasswordEncoder实例
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * 配置Spring Security的安全过滤链
     * @param http HttpSecurity对象
     * @return SecurityFilterChain实例
     * @throws Exception 配置异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 禁用CSRF保护，适合API接口
            .csrf(csrf -> csrf.disable())
            // 配置授权规则
            .authorizeHttpRequests(authorize -> authorize
                // 允许匿名访问注册和登录接口
                .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                // 其他所有请求需要认证
                .anyRequest().authenticated()
            )
            // 使用Basic认证（默认）
            .httpBasic(httpBasic -> {});
        
        return http.build();
    }
}