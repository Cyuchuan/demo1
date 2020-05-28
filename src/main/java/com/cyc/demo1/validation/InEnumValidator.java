package com.cyc.demo1.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

/**
 * @author chenyuchuan
 */
public class InEnumValidator implements ConstraintValidator<InEnum, String> {
    String methodName;
    Class enumClass;

    @Override
    public void initialize(InEnum constraintAnnotation) {
        enumClass = constraintAnnotation.inEnum();
        methodName = constraintAnnotation.methodName();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            Method values = enumClass.getDeclaredMethod("values");
            Method declaredMethod = enumClass.getDeclaredMethod(methodName);

            Enum[] enums = (Enum[])values.invoke(enumClass);

            for (Enum anEnum : enums) {
                Object invoke = declaredMethod.invoke(anEnum);

                if (invoke.equals(value)) {
                    return true;
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
