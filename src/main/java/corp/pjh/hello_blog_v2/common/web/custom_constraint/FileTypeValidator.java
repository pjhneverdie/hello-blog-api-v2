package corp.pjh.hello_blog_v2.common.web.custom_constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class FileTypeValidator implements ConstraintValidator<FileTypeConstraint, MultipartFile> {
    private final List<String> ALLOWED_EXTENSIONS = Arrays.asList("png", "jpg", "jpeg");

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        if (file == null) {
            return true;
        }

        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null) {
            return false;
        }

        String[] splitFilename = originalFilename.split("\\.");
        String extension = splitFilename[splitFilename.length - 1].toLowerCase();

        return ALLOWED_EXTENSIONS.contains(extension);
    }
}