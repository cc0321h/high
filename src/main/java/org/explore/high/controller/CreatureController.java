package org.explore.high.controller;

import org.explore.high.dto.CreatureDTO;
import org.explore.high.entity.Creature;
import org.explore.high.result.Result;
import org.explore.high.result.PageResult;
import org.explore.high.service.CreatureService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 生物控制器
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class CreatureController {
    
    @Autowired
    private CreatureService creatureService;
    
    /**
     * 添加生物
     * @param creatureDTO 生物信息
     * @return 添加结果
     */
    @PostMapping("/creature")
    public Result<String> addCreature(@RequestBody CreatureDTO creatureDTO) {
        // 将DTO转换为Entity
        Creature creature = new Creature();
        BeanUtils.copyProperties(creatureDTO, creature);
        
        // 添加生物
        boolean success = creatureService.addCreature(creature);
        if (success) {
            return Result.success("生物添加成功");
        } else {
            return Result.error("生物添加失败");
        }
    }
    
    /**
     * 获取所有生物列表（分页）
     * @param page 页码
     * @param pageSize 每页大小
     * @param keyword 搜索关键词
     * @return 生物列表
     */
    @GetMapping("/creature/getAllCreature")
    public Result<PageResult> getAllCreature(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "") String keyword) {
        // 调用服务层方法获取分页结果
        PageResult pageResult = creatureService.getAllCreature(page, pageSize, keyword);
        // 记录日志 
        log.info("获取所有生物列表（分页），页码：{}，每页大小：{}，搜索关键词：{}，结果：{}", page, pageSize, keyword, pageResult);
        // 返回结果
        return Result.success(pageResult);
    }
    
    /**
     * 更新生物信息
     * @param id 生物ID
     * @param creatureDTO 生物信息
     * @return 更新结果
     */
    @PutMapping("/creature/{id}")
    public Result<String> updateCreature(
            @PathVariable Integer id,
            @RequestBody CreatureDTO creatureDTO) {
        // 将DTO转换为Entity
        Creature creature = new Creature();
        BeanUtils.copyProperties(creatureDTO, creature);
        // 设置生物ID
        creature.setId(id);
        
        // 添加日志
        log.info("更新生物信息，ID：{}，生物信息：{}", id, creature);
        
        // 更新生物
        boolean success = creatureService.updateCreature(creature);
        if (success) {
            return Result.success("生物更新成功");
        } else {
            return Result.error("生物更新失败");
        }
    }
    
    /**
     * 删除生物
     * @param id 生物ID
     * @return 删除结果
     */
    @DeleteMapping("/creature/{id}")
    public Result<String> deleteCreature(@PathVariable Integer id) {
        // 添加日志
        log.info("删除生物，ID：{}", id);
        
        // 删除生物
        boolean success = creatureService.deleteCreature(id);
        if (success) {
            return Result.success("生物删除成功");
        } else {
            return Result.error("生物删除失败");
        }
    }
}