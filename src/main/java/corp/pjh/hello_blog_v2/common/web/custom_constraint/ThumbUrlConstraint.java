package corp.pjh.hello_blog_v2.common.web.custom_constraint;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ThumbUrlValidator.class)
public @interface ThumbUrlConstraint {
    String message() default "올바른 썸네일 주소를 입력해 주세요.";

    Class<?>[] groups() default {};

    Class[] payload() default {};
}
