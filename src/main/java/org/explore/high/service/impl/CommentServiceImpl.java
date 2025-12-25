package org.explore.high.service.impl;

import org.explore.high.entity.Comment;
import org.explore.high.mapper.CommentMapper;
import org.explore.high.result.PageResult;
import org.explore.high.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论服务实现类
 */
@Service
public class CommentServiceImpl implements CommentService {
    
    @Autowired
    private CommentMapper commentMapper;
    
    /**
     * 分页查询指定生物的已审核评论
     * @param creatureId 生物ID
     * @param page 页码
     * @param pageSize 每页大小
     * @return 分页查询结果
     */
    @Override
    public PageResult getApprovedCommentsByCreatureId(Integer creatureId, int page, int pageSize) {
        // 计算偏移量
        int offset = (page - 1) * pageSize;
        
        // 查询已审核评论列表
        List<Comment> commentList = commentMapper.selectApprovedByCreatureId(creatureId, offset, pageSize);
        
        // 查询已审核评论总数
        long total = commentMapper.countApprovedByCreatureId(creatureId);
        
        // 封装分页结果
        return new PageResult(total, commentList);
    }
    
    /**
     * 分页获取所有状态的评论
     * @param page 页码
     * @param pageSize 每页大小
     * @return 分页查询结果
     */
    @Override
    public PageResult getAllComments(int page, int pageSize) {
        // 计算偏移量
        int offset = (page - 1) * pageSize;
        
        // 查询所有状态评论列表
        List<Comment> commentList = commentMapper.selectAllComments(offset, pageSize);
        
        // 查询所有状态评论总数
        long total = commentMapper.countAllComments();
        
        // 封装分页结果
        return new PageResult(total, commentList);
    }
    
    /**
     * 更新评论状态
     * @param id 评论ID
     * @param status 新状态
     * @return 更新是否成功
     */
    @Override
    public boolean updateCommentStatus(Integer id, int status) {
        // 更新评论状态
        int rows = commentMapper.updateStatus(id, status);
        return rows > 0;
    }
    
    /**
     * 提交评论
     * @param creatureId 生物ID
     * @param userId 用户ID
     * @param content 评论内容
     * @return 提交是否成功
     */
    @Override
    public boolean submitComment(Integer creatureId, Integer userId, String content) {
        // 创建评论对象
        Comment comment = new Comment();
        comment.setCreatureId(creatureId);
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setStatus(0); // 待审核状态
        
        // 插入评论
        int rows = commentMapper.insert(comment);
        return rows > 0;
    }
}