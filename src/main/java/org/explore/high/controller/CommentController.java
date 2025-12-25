package org.explore.high.controller;

import org.explore.high.context.BaseContext;
import org.explore.high.dto.CommentDTO;
import org.explore.high.result.PageResult;
import org.explore.high.result.Result;
import org.explore.high.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;

/**
 * 评论控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    
    /**
     * 管理员获取所有状态的评论列表
     * @param page 页码
     * @param pageSize 每页大小
     * @return 评论列表
     */
    @GetMapping("/creature/admin/getComments")
    public Result<PageResult> adminGetComments(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        // 添加日志
        log.info("管理员获取所有状态评论列表，页码：{}，每页大小：{}", page, pageSize);
        
        // 调用服务层方法获取所有状态评论列表
        PageResult pageResult = commentService.getAllComments(page, pageSize);
        
        // 返回结果
        return Result.success(pageResult);
    }
    
    /**
     * 管理员删除评论（设置状态为-1）
     * @param id 评论ID
     * @return 删除结果
     */
    @PostMapping("/creature/admin/delete/{id}")
    public Result<String> adminDeleteComment(@PathVariable Integer id) {
        // 添加日志
        log.info("管理员删除评论，评论ID：{}", id);
        
        // 调用服务层方法删除评论
        boolean success = commentService.updateCommentStatus(id, -1);
        
        // 返回结果
        if (success) {
            return Result.success("评论删除成功");
        } else {
            return Result.error("评论删除失败");
        }
    }
    
    /**
     * 管理员审核通过评论（设置状态为1）
     * @param id 评论ID
     * @return 审核结果
     */
    @PostMapping("/creature/admin/review/{id}")
    public Result<String> adminReviewComment(@PathVariable Integer id) {
        // 添加日志
        log.info("管理员审核通过评论，评论ID：{}", id);
        
        // 调用服务层方法审核通过评论
        boolean success = commentService.updateCommentStatus(id, 1);
        
        // 返回结果
        if (success) {
            return Result.success("评论审核通过");
        } else {
            return Result.error("评论审核失败");
        }
    }
    
    /**
     * 用户获取指定生物的已审核评论
     * @param creatureId 生物ID
     * @param page 页码
     * @param pageSize 每页大小
     * @return 评论列表
     */
    @GetMapping("/creature/user/getComments/{creatureId}")
    public Result<PageResult> userGetComments(
            @PathVariable Integer creatureId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        // 添加日志
        log.info("用户获取生物评论列表，生物ID：{}，页码：{}，每页大小：{}", creatureId, page, pageSize);
        
        // 调用服务层方法获取已审核评论列表
        PageResult pageResult = commentService.getApprovedCommentsByCreatureId(creatureId, page, pageSize);
        
        // 返回结果
        return Result.success(pageResult);
    }
    
    /**
     * 提交评论
     * @param creatureId 生物ID
     * @param commentDTO 评论内容
     * @return 提交结果
     */
    @PostMapping("/creature/{creatureId}/comments")
    public Result<String> submitComment(
            @PathVariable Integer creatureId,
            @RequestBody CommentDTO commentDTO) {
        // 获取当前用户ID
        Integer userId = BaseContext.getCurrentId().intValue();
        
        // 添加日志
        log.info("提交生物评论，生物ID：{}，用户ID：{}，评论内容：{}", creatureId, userId, commentDTO.getContent());
        
        // 调用服务层方法提交评论
        boolean success = commentService.submitComment(creatureId, userId, commentDTO.getContent());
        
        // 返回结果
        if (success) {
            return Result.success("评论提交成功");
        } else {
            return Result.error("评论提交失败");
        }
    }
}