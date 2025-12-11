package org.explore.high.controller;

import org.explore.high.dto.UserRegisterDTO;
import org.explore.high.dto.UserLoginDTO;
import org.explore.high.dto.UserLoginResponseDTO;
import org.explore.high.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.explore.high.result.Result;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 用户注册接口
     * @param userRegisterDTO 注册请求参数
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        boolean success = userService.register(userRegisterDTO);
        if (success) {
            return Result.success("注册成功");
        } else {
            return Result.error("注册失败");
        }
    }
    
    /**
     * 用户登录接口
     * @param userLoginDTO 登录请求参数
     * @return 登录结果，包含用户信息和JWT令牌
     */
    @PostMapping("/login")
    public Result<UserLoginResponseDTO> login(@RequestBody UserLoginDTO userLoginDTO) {
        UserLoginResponseDTO responseDTO = userService.login(userLoginDTO);
        return Result.success(responseDTO);
    }
}