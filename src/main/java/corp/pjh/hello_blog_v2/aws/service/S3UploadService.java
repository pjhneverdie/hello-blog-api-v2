package corp.pjh.hello_blog_v2.aws.service;

import corp.pjh.hello_blog_v2.aws.config.AwsConfig;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class S3UploadService {
    private final Region region;
    private final String bucketName;
    private final S3Client s3Client;
    private final S3Utilities s3Utilities;

    public S3UploadService(AwsConfig awsConfig, String bucketName, S3Client s3Client, S3Utilities s3Utilities) {
        this.region = Region.of(awsConfig.getRegionName());
        this.bucketName = bucketName;
        this.s3Client = s3Client;
        this.s3Utilities = s3Utilities;
    }

    public String putFile(MultipartFile multipartFile, String key) throws IOException {
        PutObjectRequest objectRequest = PutObjectRequest
                .builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(
                objectRequest,
                RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize())
        );

        GetUrlRequest getUrlRequest = GetUrlRequest
                .builder()
                .region(region)
                .bucket(bucketName)
                .key(key)
                .build();

        return s3Utilities.getUrl(getUrlRequest).toString();
    }
}
