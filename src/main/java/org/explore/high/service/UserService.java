package org.explore.high.service;

import org.explore.high.dto.UserRegisterDTO;
import org.explore.high.dto.UserLoginDTO;
import org.explore.high.dto.UserLoginResponseDTO;

/**
 * 用户服务接口
 */
public interface UserService {
    
    /**
     * 用户注册
     * @param userRegisterDTO 用户注册信息
     * @return 注册是否成功
     */
    boolean register(UserRegisterDTO userRegisterDTO);
    
    /**
     * 用户登录
     * @param userLoginDTO 用户登录信息
     * @return 登录成功后的用户信息和令牌
     */
    UserLoginResponseDTO login(UserLoginDTO userLoginDTO);
}