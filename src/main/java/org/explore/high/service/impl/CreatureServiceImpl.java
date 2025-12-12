package org.explore.high.service.impl;

import org.explore.high.entity.Creature;
import org.explore.high.mapper.CreatureMapper;
import org.explore.high.result.PageResult;
import org.explore.high.service.CreatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 生物服务实现类
 */
@Service
public class CreatureServiceImpl implements CreatureService {
    
    @Autowired
    private CreatureMapper creatureMapper;
    
    /**
     * 添加生物
     * @param creature 生物信息
     * @return 添加是否成功
     */
    @Override
    public boolean addCreature(Creature creature) {
        int rows = creatureMapper.insert(creature);
        return rows > 0;
    }
    
    /**
     * 分页查询生物列表
     * @param page 页码
     * @param pageSize 每页大小
     * @param keyword 搜索关键词
     * @return 分页查询结果
     */
    @Override
    public PageResult getAllCreature(int page, int pageSize, String keyword) {
        // 计算偏移量
        int offset = (page - 1) * pageSize;
        
        // 查询生物列表
        List<Creature> creatureList = creatureMapper.selectPage(offset, pageSize, keyword);
        
        // 查询总数
        long total = creatureMapper.count(keyword);
        
        // 封装分页结果
        return new PageResult(total, creatureList);
    }
    
    /**
     * 更新生物信息
     * @param creature 生物信息
     * @return 更新是否成功
     */
    @Override
    public boolean updateCreature(Creature creature) {
        int rows = creatureMapper.update(creature);
        return rows > 0;
    }
    
    /**
     * 根据ID删除生物
     * @param id 生物ID
     * @return 删除是否成功
     */
    @Override
    public boolean deleteCreature(Integer id) {
        int rows = creatureMapper.deleteById(id);
        
        return rows > 0;
    }
}