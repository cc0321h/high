package org.explore.high.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.explore.high.entity.Comment;

import java.util.List;

/**
 * 评论Mapper接口
 */
@Mapper
public interface CommentMapper {
    
    /**
     * 分页查询指定生物的已审核评论
     * @param creatureId 生物ID
     * @param offset 偏移量
     * @param pageSize 每页大小
     * @return 评论列表
     */
    @Select("SELECT * FROM creature_comment WHERE creature_id = #{creatureId} AND status = 1 ORDER BY created_at DESC LIMIT #{offset}, #{pageSize}")
    List<Comment> selectApprovedByCreatureId(Integer creatureId, int offset, int pageSize);
    
    /**
     * 查询指定生物的已审核评论总数
     * @param creatureId 生物ID
     * @return 评论总数
     */
    @Select("SELECT COUNT(*) FROM creature_comment WHERE creature_id = #{creatureId} AND status = 1")
    long countApprovedByCreatureId(Integer creatureId);
    
    /**
     * 分页查询所有状态的评论
     * @param offset 偏移量
     * @param pageSize 每页大小
     * @return 评论列表
     */
    @Select("SELECT * FROM creature_comment ORDER BY created_at DESC LIMIT #{offset}, #{pageSize}")
    List<Comment> selectAllComments(int offset, int pageSize);
    
    /**
     * 查询所有状态的评论总数
     * @return 评论总数
     */
    @Select("SELECT COUNT(*) FROM creature_comment")
    long countAllComments();
    
    /**
     * 更新评论状态
     * @param id 评论ID
     * @param status 新状态
     * @return 影响的行数
     */
    @Update("UPDATE creature_comment SET status = #{status} WHERE id = #{id}")
    int updateStatus(Integer id, int status);
    
    /**
     * 插入评论
     * @param comment 评论信息
     * @return 影响的行数
     */
    @Insert("INSERT INTO creature_comment(user_id, creature_id, content, status) VALUES(#{userId}, #{creatureId}, #{content}, #{status})")
    int insert(Comment comment);
}