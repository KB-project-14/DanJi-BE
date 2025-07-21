package org.danji.transaction.validator;

import org.danji.transaction.annotation.MultipleOfHundred;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultipleOfHundredValidator implements ConstraintValidator<MultipleOfHundred, Integer> {

    @Override
    public void initialize(MultipleOfHundred constraintAnnotation) {
        // 초기화 로직이 필요 없다면 비워둬도 됨
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) return true; // @NotNull과 함께 사용할 경우 null 허용
        return value % 100 == 0;
    }
}
