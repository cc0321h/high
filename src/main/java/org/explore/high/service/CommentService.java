package org.explore.high.service;

import org.explore.high.entity.Comment;
import org.explore.high.result.PageResult;

import java.util.List;

/**
 * 评论服务接口
 */
public interface CommentService {
    
    /**
     * 分页查询指定生物的已审核评论
     * @param creatureId 生物ID
     * @param page 页码
     * @param pageSize 每页大小
     * @return 分页查询结果
     */
    PageResult getApprovedCommentsByCreatureId(Integer creatureId, int page, int pageSize);
    
    /**
     * 分页获取所有状态的评论
     * @param page 页码
     * @param pageSize 每页大小
     * @return 分页查询结果
     */
    PageResult getAllComments(int page, int pageSize);
    
    /**
     * 更新评论状态
     * @param id 评论ID
     * @param status 新状态
     * @return 更新是否成功
     */
    boolean updateCommentStatus(Integer id, int status);
    
    /**
     * 提交评论
     * @param creatureId 生物ID
     * @param userId 用户ID
     * @param content 评论内容
     * @return 提交是否成功
     */
    boolean submitComment(Integer creatureId, Integer userId, String content);
}