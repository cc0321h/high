package org.explore.high.dto;

import lombok.Data;

/**
 * 评论DTO类，用于接收前端提交的评论数据
 */
@Data
public class CommentDTO {
    /**
     * 评论内容
     */
    private String content;
}