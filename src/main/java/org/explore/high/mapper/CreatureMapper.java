package org.explore.high.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.explore.high.entity.Creature;

import java.util.List;

/**
 * 生物Mapper接口
 */
@Mapper
public interface CreatureMapper {
    
    /**
     * 插入生物信息
     * @param creature 生物信息
     * @return 影响的行数
     */
    @Insert("INSERT INTO creature(name, image_url, status, description, habitat, population) VALUES(#{name}, #{imageUrl}, #{status}, #{description}, #{habitat}, #{population})")
    int insert(Creature creature);
    
    /**
     * 分页查询生物列表
     * @param offset 偏移量
     * @param pageSize 每页大小
     * @param keyword 搜索关键词
     * @return 生物列表
     */
    @Select("SELECT * FROM creature WHERE name LIKE CONCAT('%', #{keyword}, '%') LIMIT #{offset}, #{pageSize}")
    List<Creature> selectPage(int offset, int pageSize, String keyword);
    
    /**
     * 查询生物总数
     * @param keyword 搜索关键词
     * @return 生物总数
     */
    @Select("SELECT COUNT(*) FROM creature WHERE name LIKE CONCAT('%', #{keyword}, '%')")
    long count(String keyword);
    
    /**
     * 根据ID更新生物信息
     * @param creature 生物信息
     * @return 影响的行数
     */
    @Update("UPDATE creature SET name = #{name}, image_url = #{imageUrl}, status = #{status}, description = #{description}, habitat = #{habitat}, population = #{population} WHERE id = #{id}")
    int update(Creature creature);
    
    /**
     * 根据ID删除生物
     * @param id 生物ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM creature WHERE id = #{id}")
    int deleteById(Integer id);
}