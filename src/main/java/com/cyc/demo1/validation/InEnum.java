package com.cyc.demo1.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 验证需要
 * 
 * @author chenyuchuan
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(InEnum.List.class)
@Documented
@Constraint(validatedBy = {InEnumValidator.class})
public @interface InEnum {
    String message() default "需要在指定的枚举内";

    /**
     * 需要使用的枚举的哪个方法获取，没有的话，默认使用枚举实例name
     * 
     * @return 获取的枚举的方法时间
     */
    String methodName() default "";

    /**
     * 需要校验的枚举
     * 
     * @return 校验的枚举类
     */
    Class<?> inEnum();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        InEnum[] value();
    }
}
