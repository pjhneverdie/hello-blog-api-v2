package corp.pjh.hello_blog_v2.aws.exception;

import corp.pjh.hello_blog_v2.common.dto.ExceptionInfo;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatusCode;

@RequiredArgsConstructor
public enum S3ExceptionInfo implements ExceptionInfo {
    KEY_EXTRACTION_FAILED("KEY_EXTRACTION_FAILED", "파일 접근 과정에서 오류가 발생했습니다.", HttpStatusCode.valueOf(500));

    @Override
    public String codeName() {
        return this.codeName;
    }

    @Override
    public String errorMessage() {
        return this.errorMessage;
    }

    @Override
    public HttpStatusCode httpStatusCode() {
        return this.httpStatusCode;
    }

    private final String codeName;
    private final String errorMessage;
    private final HttpStatusCode httpStatusCode;
}
