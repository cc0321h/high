package org.explore.high.dto;

import lombok.Data;

/**
 * 用户注册请求DTO
 */
@Data
public class UserRegisterDTO {
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 密码
     */
    private String password;

    /**
     * 角色
     */
    private String role;
}