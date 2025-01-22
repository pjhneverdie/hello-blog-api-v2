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
        String fileName = "test-file.txt";
        String fileContent = "This is a test file for S3 upload.";
        String contentType = "text/plain";

        MultipartFile multipartFile = createMultipartFile(fileName, fileContent, contentType);
        String key = "test-folder/" + fileName + "-" + TimeUtil.getLocalDateTimeFormatUTC();

        String uploadedUrl = s3UploadService.putFile(multipartFile, key);

        assertNotNull(uploadedUrl);
        assertEquals(s3UploadService.extractKeyFromUrl(uploadedUrl), key);

        s3UploadService.deleteFile(s3UploadService.extractKeyFromUrl(uploadedUrl));
    }

    public static MultipartFile createMultipartFile(String fileName, String fileContent, String contentType) throws IOException {
        return new MockMultipartFile(
                fileName,
                fileName,
                contentType,
                fileContent.getBytes()
        );
    }
}