package corp.pjh.hello_blog_v2.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
public class ApiResponse<T> {
    private final String codeName;

    private final String errorMessage;

    private final T value;

    public static <T> ApiResponse<T> successResponse(T value) {
        return new ApiResponse<T>("OK", null, value);
    }

    public static <T> ApiResponse<T> successVoidResponse() {
        return new ApiResponse<T>("OK", null, null);
    }

    public static <T> ApiResponse<T> failedResponse(ExceptionInfo exceptionInfo) {
        return new ApiResponse<T>(exceptionInfo.errorId(), exceptionInfo.errorMessage(), null);
    }
}
