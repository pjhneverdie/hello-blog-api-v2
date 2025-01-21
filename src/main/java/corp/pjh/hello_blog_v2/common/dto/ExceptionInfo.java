package corp.pjh.hello_blog_v2.common.dto;

import org.springframework.http.HttpStatusCode;

public interface ExceptionInfo {
    String codeName();

    String errorMessage();

    HttpStatusCode httpStatusCode();
}
