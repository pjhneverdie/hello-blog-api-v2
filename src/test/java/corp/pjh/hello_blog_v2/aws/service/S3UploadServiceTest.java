package corp.pjh.hello_blog_v2.aws.service;

import corp.pjh.hello_blog_v2.common.util.TimeUtil;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class S3UploadServiceTest {
    @Autowired
    private S3UploadService s3UploadService;

    @Test
    void 파일_단건_업로드_및_삭제_테스트() throws IOException {
        // Given
        MultipartFile multipartFile = createMultipartFile(
                "test-file.txt",
                "This is a test file for S3 upload.",
                "text/plain"
        );

        String key = "test-folder/" + TimeUtil.getUTCLocalDatetime() + "." + multipartFile.getContentType().split("/")[1];

        // When
        // 업로드가 잘 되는지 확인
        String uploadedUrl = s3UploadService.putFile(multipartFile, key);

        // Then
        // url에서 제대로 key를 추출할 수 있는지 확인
        assertEquals(key, s3UploadService.extractKeyFromUrl(uploadedUrl));


        s3UploadService.deleteFile(s3UploadService.extractKeyFromUrl(uploadedUrl));
    }

    public static MultipartFile createMultipartFile(String fileName, String fileContent, String contentType) {
        return new MockMultipartFile(
                fileName,
                fileName,
                contentType,
                fileContent.getBytes()
        );
    }
}