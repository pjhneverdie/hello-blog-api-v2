package corp.pjh.hello_blog_v2.member.execption;

import corp.pjh.hello_blog_v2.common.dto.ExceptionInfo;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatusCode;

@RequiredArgsConstructor
public enum MemberExceptionInfo implements ExceptionInfo {
    UNAUTHORIZED("UNAUTHORIZED", "로그인이 필요한 작업입니다.", HttpStatusCode.valueOf(401)),
    LOGIN_FAILED("LOGIN_FAILED", "음.. 다시 시도해 주세요!!", HttpStatusCode.valueOf(400));

    @Override
    public String errorId() {
        return errorId;
    }

    @Override
    public String errorMessage() {
        return errorMessage;
    }

    @Override
    public HttpStatusCode httpStatusCode() {
        return httpStatusCode;
    }

    private final String errorId;
    private final String errorMessage;
    private final HttpStatusCode httpStatusCode;
}
