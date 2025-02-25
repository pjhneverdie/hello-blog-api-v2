package corp.pjh.hello_blog_v2.common.dto;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatusCode;

@RequiredArgsConstructor
public class ValidationFailedExceptionInfo extends GlobalExceptionInfo {
    private final String errorMessage;

    @Override
    public String errorId() {
        return "VALIDATION_FAILED";
    }

    @Override
    public String errorMessage() {
        return this.errorMessage;
    }

    @Override
    public HttpStatusCode httpStatusCode() {
        return HttpStatusCode.valueOf(400);
    }
}
