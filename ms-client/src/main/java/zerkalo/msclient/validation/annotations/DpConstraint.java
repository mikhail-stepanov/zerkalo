package zerkalo.msclient.validation.annotations;

import zerkalo.msclient.validation.validator.DpConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = DpConstraintValidator.class
)
public @interface DpConstraint {

    Class<?> type() default String.class;

    boolean notnull() default true;

    String pattern() default "";

    int length() default -1;

    String message() default "{message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}