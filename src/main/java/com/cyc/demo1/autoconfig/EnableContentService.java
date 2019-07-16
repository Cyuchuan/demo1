package com.cyc.demo1.autoconfig;

import java.lang.annotation.*;

import org.springframework.context.annotation.Import;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ContentServiceSelector.class)
public @interface EnableContentService {

    String value() default "B";
}
