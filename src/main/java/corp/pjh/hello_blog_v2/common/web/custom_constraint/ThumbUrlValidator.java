package corp.pjh.hello_blog_v2.common.web.custom_constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ThumbUrlValidator implements ConstraintValidator<ThumbUrlConstraint, String> {
    private final String regex = "^(https?://)([\\w.-]+)(:\\d+)?(/([\\w/_-]+)?)*(\\?[\\w=&]*)?(#[\\w-]*)?$";
    private final Pattern pattern = Pattern.compile(regex);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        return !value.trim().isEmpty() && pattern.matcher(value).matches();
    }
}