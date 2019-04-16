package com.cyc.demo1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidationField {
    boolean value() default true;

    Class<? extends Enum> enumerationClass() default Enum.class;

    String enumerationField() default "";

    String regex() default "";

    String regexErrorMessage() default "";
}
