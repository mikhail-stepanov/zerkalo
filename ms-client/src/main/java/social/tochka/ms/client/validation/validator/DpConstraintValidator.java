package social.tochka.ms.client.validation.validator;

import social.tochka.ms.client.validation.annotations.DpConstraint;
import social.tochka.ms.client.validation.util.ValidationMap;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DpConstraintValidator implements ConstraintValidator<DpConstraint, Object> {

    private Class<?> type;
    private boolean notNull;
    private String pattern;
    private int length;

    @Override
    public void initialize(DpConstraint parameters) {
        this.notNull = parameters.notnull();
        this.pattern = parameters.pattern();
        this.type = parameters.type();
        this.length = parameters.length();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        if (notNull && object == null) return false;

        if (!type.equals(String.class)) {
            if (!ValidationMap.types.containsKey(type)) {
                //TODO: unsupported exception
            }
            return ValidationMap.types.get(type).apply(object);
        }

        if (!pattern.equals("") && !object.toString().matches(pattern)) return false;

        return true;
    }
}