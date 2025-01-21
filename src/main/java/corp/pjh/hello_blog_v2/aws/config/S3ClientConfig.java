package corp.pjh.hello_blog_v2.aws.config;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;

@Configuration
@RequiredArgsConstructor
public class S3ClientConfig {
    private final AwsConfig awsConfig;

    @Bean
    public String bucketName(@Value("${aws.s3.bucket-name}") String bucketName) {
        return bucketName;
    }

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(awsConfig.getAccessKeyId(), awsConfig.getSecretAccessKey());

        return S3Client
                .builder()
                .region(Region.of(awsConfig.getRegionName()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    @Bean
    public S3Utilities s3Utilities() {
        return S3Utilities
                .builder()
                .region(Region.of(awsConfig.getRegionName()))
                .build();
    }
}