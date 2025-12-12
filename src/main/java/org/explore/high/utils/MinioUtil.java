package org.explore.high.utils;

import java.io.InputStream;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
public class MinioUtil {

    private String url;
    private String accessKey;
    private String secretKey;
    private String bucketName;

    /**
     * 上传文件
     * @param inputStream 文件输入流
     * @param objectName 文件名
     */
    public String upload(InputStream inputStream, String objectName) {
        log.info("上传文件");

        try (MinioClient minioClient = MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build()) {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(inputStream, inputStream.available(), -1).build());
        }
        catch (Exception e) {
            log.error("上传文件失败", e);
        }
        return new StringBuilder(url).append("/").append(bucketName).append("/").append(objectName).toString();
    }

    public void delete(String fileUrl) {
        try (MinioClient minioClient = MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build()) {
            // 提取objectName
            String objectName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        }
        catch (Exception e) {
            log.error("删除文件失败", e);
        }
    }
}
