package corp.pjh.hello_blog_v2.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final ExceptionInfo exceptionInfo;
}
