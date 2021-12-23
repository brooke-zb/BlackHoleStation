package top.brookezb.bhs.proerties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author brooke_zb
 */
@Data
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {
    private String accessKey;
    private String accessSecret;
    private String endPoint;
    private String bucketName;
    private String urlPrefix;
    private String namespace;
}
