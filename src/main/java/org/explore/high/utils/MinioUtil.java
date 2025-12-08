package org.explore.high.utils;

import java.io.InputStream;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
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
        return url + "/" + bucketName + "/" + objectName;
    }
}
