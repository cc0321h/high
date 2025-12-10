package org.explore.high.config;

import org.explore.high.interceptor.JwtTokenUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security配置类
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;
    
    /**
     * 配置SecurityFilterChain
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 禁用CSRF
            .csrf(csrf -> csrf.disable())
            // 不使用Session
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 配置URL访问权限
            .authorizeHttpRequests(authorize -> authorize
                // 允许登录和注册接口匿名访问
                .requestMatchers("/api/users/login", "/api/users/register").permitAll()
                // 其他所有请求需要认证
                .anyRequest().authenticated()
            )
            // 添加JWT过滤器
            .addFilterBefore(jwtTokenUserInterceptor, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    /**
     * 获取AuthenticationManager
     * @param authenticationConfiguration AuthenticationConfiguration
     * @return AuthenticationManager
     * @throws Exception 异常
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    /**
     * 配置BCryptPasswordEncoder
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}