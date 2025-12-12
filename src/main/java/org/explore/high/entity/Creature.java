package org.explore.high.entity;

import lombok.Data;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 生物实体类
 */
@Data
public class Creature {
    /**
     * 主键ID
     */
    private Integer id;
    
    /**
     * 生物名称
     */
    private String name;
    
    /**
     * 图片URL
     */
    private String imageUrl;
    
    /**
     * 状态：SA（安全）、VN（易危）、EN（濒危）
     */
    private String status;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 栖息地
     */
    private String habitat;
    
    /**
     * 种群数量
     */
    private String population;
    
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}