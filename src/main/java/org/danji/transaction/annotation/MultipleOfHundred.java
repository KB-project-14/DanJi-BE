package org.danji.transaction.annotation;

import org.danji.transaction.validator.MultipleOfHundredValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = MultipleOfHundredValidator.class)
public @interface MultipleOfHundred {

    String message() default "금액은 100원 단위여야 합니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
