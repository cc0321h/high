package org.explore.high.service.impl;

import org.explore.high.entity.User;
import org.explore.high.mapper.UserMapper;
import org.explore.high.dto.UserRegisterDTO;
import org.explore.high.dto.UserLoginDTO;
import org.explore.high.dto.UserLoginResponseDTO;
import org.explore.high.service.UserService;
import org.explore.high.utils.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${high.jwt.user-secret-key}")
    private String userSecretKey;
    
    @Value("${high.jwt.user-ttl}")
    private long userTtl;
    
    /**
     * 用户注册
     * @param userRegisterDTO 用户注册信息
     * @return 注册是否成功
     */
    @Override
    public boolean register(UserRegisterDTO userRegisterDTO) {
        // 检查用户名是否已存在
        User existingUserByUsername = userMapper.findByUsername(userRegisterDTO.getUsername());
        Assert.isNull(existingUserByUsername, "用户名已存在");
        
        // 检查邮箱是否已存在
        User existingUserByEmail = userMapper.findByEmail(userRegisterDTO.getEmail());
        Assert.isNull(existingUserByEmail, "邮箱已存在");
        
        // 对密码进行加密
        String encodedPassword = passwordEncoder.encode(userRegisterDTO.getPassword());
        userRegisterDTO.setPassword(encodedPassword);
        
        // 创建用户对象
        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);
        
        // 设置默认状态
        user.setStatus("active");
        
        // 插入用户信息
        int rows = userMapper.insert(user);
        return rows > 0;
    }
    
    /**
     * 用户登录
     * @param userLoginDTO 用户登录信息
     * @return 登录成功后的用户信息和令牌
     */
    @Override
    public UserLoginResponseDTO login(UserLoginDTO userLoginDTO) {
        // 根据用户名查询用户
        User user = userMapper.findByUsername(userLoginDTO.getUsername());
        Assert.notNull(user, "用户名或密码错误");
        
        // 检查用户状态是否为active
        Assert.isTrue("active".equals(user.getStatus()), "用户已被禁用");
        
        // 验证密码
        boolean passwordMatch = passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword());
        Assert.isTrue(passwordMatch, "用户名或密码错误");
        
        // 生成JWT令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
        claims.put("role", user.getRole());
        String token = JwtUtil.createJWT(userSecretKey, userTtl, claims);
        
        // 封装登录响应DTO
        UserLoginResponseDTO responseDTO = new UserLoginResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setUsername(user.getUsername());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setRole(user.getRole());
        responseDTO.setStatus(user.getStatus());
        responseDTO.setToken(token);
        
        return responseDTO;
    }
}