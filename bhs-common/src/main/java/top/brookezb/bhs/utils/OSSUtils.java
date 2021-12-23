package top.brookezb.bhs.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.brookezb.bhs.entity.R;
import top.brookezb.bhs.proerties.OssProperties;

import java.io.InputStream;

/**
 * @author brooke_zb
 */
@Slf4j
@Service
public class OSSUtils {
    private final OSS ossClient;

    private final String bucketName;
    private final String namespace;
    private final String urlPrefix;

    public OSSUtils(OssProperties properties) {
        this.bucketName = properties.getBucketName();
        this.namespace = properties.getNamespace();
        this.urlPrefix = properties.getUrlPrefix();

        this.ossClient = new OSSClientBuilder().build(
                properties.getEndPoint(),
                properties.getAccessKey(),
                properties.getAccessSecret()
        );
        log.info("OSS客户端初始化成功, bucket: {}", bucketName);
    }

    /**
     * 上传文件，以IO流方式
     *
     * @param inputStream 输入流
     * @param objectName  唯一objectName（在oss中的文件名字）
     */
    public R<?> uploadStream(InputStream inputStream, String objectName) {
        try {
            ossClient.putObject(bucketName, namespace + objectName, inputStream);
            return R.success(urlPrefix + namespace + objectName, "上传成功");
        } catch (Exception e) {
            log.error("OSS文件上传失败", e);
            throw e;
        }
    }

    /**
     * 上传文件，以MuiltPart方式
     *
     * @param file 文件
     * @param objectName  唯一objectName（在oss中的文件名字）
     */
    public R<?> uploadMultipart(MultipartFile file, String objectName) throws Exception {
        return uploadStream(file.getInputStream(), objectName);
    }

    /**
     * 删除OSS中的单个文件
     *
     * @param objectName 唯一objectName（在oss中的文件名字）
     */
    public boolean delete(String objectName) {
        try {
            ossClient.deleteObject(bucketName, namespace + objectName);
            return true;
        } catch (Exception e) {
            log.error("OSS文件删除失败", e);
            return false;
        }
    }
}
