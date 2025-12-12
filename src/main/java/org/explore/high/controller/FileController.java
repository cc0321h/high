package org.explore.high.controller;

import org.explore.high.result.Result;
import org.explore.high.utils.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件控制器
 */
@RestController
@RequestMapping("/api")
public class FileController {
    
    @Autowired
    private MinioUtil minioUtil;
    
    /**
     * 文件上传接口
     * @param file 上传的文件
     * @return 文件访问URL
     */
    @PostMapping("/upload")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 检查文件是否为空
            if (file.isEmpty()) {
                return Result.error("上传文件不能为空");
            }
            
            // 调用MinioUtil上传文件，使用原文件名
            String fileUrl = minioUtil.upload(file.getInputStream(), file.getOriginalFilename());
            
            // 返回文件访问URL
            return Result.success(fileUrl);
        } catch (IOException e) {
            return Result.error("文件上传失败: " + e.getMessage());
        } catch (Exception e) {
            return Result.error("上传失败: " + e.getMessage());
        }
    }

    /**
     * 文件删除接口
     * @param fileUrl 文件访问URL
     * @return 删除结果
     */
    @PostMapping("/delete")
    public Result<String> deleteFile(@RequestParam("fileUrl") String fileUrl) {
        try {
            // 调用MinioUtil删除文件
            minioUtil.delete(fileUrl);
            // 返回删除成功
            return Result.success("文件删除成功");
        } catch (Exception e) {
            return Result.error("文件删除失败: " + e.getMessage());
        }
    }
}