package org.explore.high.service;

import org.explore.high.entity.Creature;
import org.explore.high.result.PageResult;

/**
 * 生物服务接口
 */
public interface CreatureService {
    
    /**
     * 添加生物
     * @param creature 生物信息
     * @return 添加是否成功
     */
    boolean addCreature(Creature creature);
    
    /**
     * 分页查询生物列表
     * @param page 页码
     * @param pageSize 每页大小
     * @param keyword 搜索关键词
     * @return 分页查询结果
     */
    PageResult getAllCreature(int page, int pageSize, String keyword);
    
    /**
     * 更新生物信息
     * @param creature 生物信息
     * @return 更新是否成功
     */
    boolean updateCreature(Creature creature);
    
    /**
     * 根据ID删除生物
     * @param id 生物ID
     * @return 删除是否成功
     */
    boolean deleteCreature(Integer id);
}