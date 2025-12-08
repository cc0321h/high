package org.explore.high.dto;

import lombok.Data;

/**
 * 用户登录响应DTO
 */
@Data
public class UserLoginResponseDTO {
    /**
     * 用户ID
     */
    private Integer id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 角色
     */
    private String role;
    
    /**
     * 状态
     */
    private String status;
    
    /**
     * JWT令牌
     */
    private String token;
}