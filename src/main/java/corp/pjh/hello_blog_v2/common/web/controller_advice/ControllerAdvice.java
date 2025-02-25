package corp.pjh.hello_blog_v2.common.web.controller_advice;

import corp.pjh.hello_blog_v2.common.dto.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error(e.getMessage(), e);

        ExceptionInfo exceptionInfo = new UnHandledExceptionInfo();

        return new ResponseEntity<>(ApiResponse.failedResponse(exceptionInfo), exceptionInfo.httpStatusCode());
    }

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
        ExceptionInfo exceptionInfo = e.getExceptionInfo();

        return new ResponseEntity<>(ApiResponse.failedResponse(exceptionInfo), exceptionInfo.httpStatusCode());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ExceptionInfo exceptionInfo = new ValidationFailedExceptionInfo(e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", ")));

        return new ResponseEntity<>(ApiResponse.failedResponse(exceptionInfo), exceptionInfo.httpStatusCode());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolationException(ConstraintViolationException e) {
        ExceptionInfo exceptionInfo = new ValidationFailedExceptionInfo(
                e.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", ")));

        return new ResponseEntity<>(ApiResponse.failedResponse(exceptionInfo), exceptionInfo.httpStatusCode());
    }
}
