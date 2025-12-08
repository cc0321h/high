package org.explore.high.dto;

import lombok.Data;

/**
 * 用户登录请求DTO
 */
@Data
public class UserLoginDTO {
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
}