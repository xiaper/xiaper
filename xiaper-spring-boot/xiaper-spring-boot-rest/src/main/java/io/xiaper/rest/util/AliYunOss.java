package io.xiaper.rest.util;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 阿里云OSS对象存储服务工具类
 *
 * 参考：
 * @see <a href="https://blog.csdn.net/Holmofy/article/details/79102577">url</a>
 *
 * 阿里云官方文档：
 * @see <a href="https://help.aliyun.com/document_detail/32013.html?spm=a2c4g.11186623.6.681.NK35jK"></a>
 * @see <a href="https://github.com/aliyun/aliyun-oss-java-sdk/blob/master/src/samples/SimpleGetObjectSample.java"></a>
 *
 * @author bytedesk.com
 */
@Component
public class AliYunOss {

    @Value("${aliyun.access.key.id}")
    private String accessKeyId;

    @Value("${aliyun.access.key.secret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.endpoint}")
    private String endPoint;

    @Value("${aliyun.oss.bucket.name}")
    private String bucketName;

    @Value("${aliyun.oss.base.url}")
    private String baseUrl;

    @Bean
    public OSSClient createOSSClient() {
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        return new OSSClient(endPoint, credentialsProvider, clientConfiguration);
    }

    @Autowired
    private OSS ossClient;


    public String uploadAvatar(String fileName,File file) {

        String folder = "avatars/";
        return uploadCommon(folder, fileName, file);
    }


    public String uploadImage(String fileName,File file) {

        String folder = "images/";
        return uploadCommon(folder, fileName, file);
    }


    public String saveWeChatImageUrl(String fileName, String url) {

        String folder = "wechat/images/";
        InputStream inputStream = null;
        try {
            inputStream = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ossClient.putObject(bucketName, folder + fileName, inputStream);
        return baseUrl + folder + fileName;
    }

    public String saveWeChatImage(String fileName, File file) {

        String folder = "wechat/images/";
        ossClient.putObject(bucketName, folder + fileName, file);
        return baseUrl + folder + fileName;
    }

    public String saveWeChatVoice(String fileName, File file) {

        String folder = "wechat/voices/";
        ossClient.putObject(bucketName, folder + fileName, file);
        return baseUrl + folder + fileName;
    }

    public String saveWeChatVideo(String fileName, File file) {

        String folder = "wechat/videos/";
        ossClient.putObject(bucketName, folder + fileName, file);
        return baseUrl + folder + fileName;
    }

    public String saveWeChatThumb(String fileName, File file) {

        String folder = "wechat/thumbs/";
        ossClient.putObject(bucketName, folder + fileName, file);
        return baseUrl + folder + fileName;
    }



    public String uploadFile(String fileName,File file) {

        String folder = "files/";
        return uploadCommon(folder, fileName, file);
    }



    public String uploadWeChatDb(String fileName,File file) {

        String folder = "wechat/db/";
        return uploadCommon(folder, fileName, file);
    }


    private String uploadCommon(String folder, String fileName,File file) {

        ossClient.putObject(bucketName, folder + fileName, file);
        return baseUrl + folder + fileName;
    }






}
