package org.explore.high.interceptor;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.explore.high.context.BaseContext;
import org.explore.high.properties.JwtProperties;
import org.explore.high.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT令牌校验的过滤器
 */
@AllArgsConstructor
@Component
@Slf4j
public class JwtTokenUserInterceptor extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;

    /**
     * 校验JWT令牌
     *
     * @param request 请求
     * @param response 响应
     * @param filterChain 过滤器链
     * @throws ServletException 异常
     * @throws IOException 异常
     */
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getUserTokenName());

        //2、如果没有令牌，直接放行，后续Spring Security会处理
        if (token == null || token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        //3、处理令牌前缀，如果有Bearer前缀则移除
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        //4、校验令牌
        try {
            log.info("JWT校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            // 注意：这里使用的是"userId"，而不是"userID"，保持与生成令牌时的一致性
            Integer userId = Integer.valueOf(claims.get("userId").toString());
            log.info("当前用户ID：{}", userId);
            BaseContext.setCurrentId(userId.longValue());
            
            //4、设置认证信息到上下文
            // 创建一个UsernamePasswordAuthenticationToken对象，并设置到SecurityContext中
            // 这里可以根据需要创建自定义的Authentication对象
            UsernamePasswordAuthenticationToken authentication = 
                new UsernamePasswordAuthenticationToken(
                    userId, null, java.util.Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            //5、通过，继续执行过滤器链
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            //6、不通过，响应401状态码
            log.error("JWT校验失败：{}", ex.getMessage());
            response.setStatus(401);
        }
    }
}
