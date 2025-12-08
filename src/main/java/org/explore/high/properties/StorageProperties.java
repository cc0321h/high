package org.explore.high.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "high.s3")
public class StorageProperties {
    private String url;
    private String accessKey;
    private String secretKey;
    private String bucketName;

}
