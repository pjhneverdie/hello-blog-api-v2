package corp.pjh.hello_blog_v2.category.exception;

import corp.pjh.hello_blog_v2.common.dto.ExceptionInfo;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatusCode;

@RequiredArgsConstructor
public enum CategoryExceptionInfo implements ExceptionInfo {
    THUMBNAIL_UPLOAD_FAILED("THUMBNAIL_UPLOAD_FAILED", "썸네일 업로드 중 오류가 발생했습니다.", HttpStatusCode.valueOf(500));

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
