package corp.pjh.hello_blog_v2.common.web.custom_constraint;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileTypeValidator.class)
public @interface FileTypeConstraint {
    String message() default "지원하지 않는 파일 형식입니다.";

    Class<?>[] groups() default {};

    Class[] payload() default {};
}
