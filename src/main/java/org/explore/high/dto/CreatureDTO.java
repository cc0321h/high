package org.explore.high.dto;

import lombok.Data;

/**
 * 生物DTO类，用于接收前端传来的数据
 */
@Data
public class CreatureDTO {
    /**
     * 生物ID
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
}