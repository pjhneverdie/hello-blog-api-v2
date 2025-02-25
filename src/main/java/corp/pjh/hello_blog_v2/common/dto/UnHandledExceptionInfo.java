package corp.pjh.hello_blog_v2.common.dto;

import org.springframework.http.HttpStatusCode;

public class UnHandledExceptionInfo extends GlobalExceptionInfo {
    @Override
    public String errorId() {
        return "INTERNAL_SERVER_ERROR";
    }

    @Override
    public String errorMessage() {
        return "서버에 오류가 발생했습니다. 잠시 후에 다시 시도해 주세요.";
    }

    @Override
    public HttpStatusCode httpStatusCode() {
        return HttpStatusCode.valueOf(500);
    }
}
