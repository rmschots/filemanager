package be.rmangels.filemanager.infrastructure.util;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.validation.Validation.buildDefaultValidatorFactory;

public class Validator {
    private static Validator instance;

    public static Validator validator() {
        if (instance == null) {
            instance = new Validator();
        }
        return instance;
    }

    private javax.validation.Validator validator;

    private Validator() {
        validator = buildDefaultValidatorFactory().getValidator();
    }

    public void validate(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(
                    constraintViolationErrorMessage(violations),
                    new HashSet<ConstraintViolation<?>>(violations));
        }
    }

    private String constraintViolationErrorMessage(Set<ConstraintViolation<Object>> violations) {
        return violations.stream()
                .map(violation -> String.format("%s#%s can not have value %s because %s", violation.getRootBeanClass(), violation.getPropertyPath(), violation.getInvalidValue(), violation.getMessage()))
                .distinct()
                .collect(Collectors.joining(", "));
    }
}
