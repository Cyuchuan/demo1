package com.cyc.demo1.autoconfig;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author chenyuchuan
 */
public class ContentServiceSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Class<?> annotationType = EnableContentService.class;
        AnnotationAttributes attributes = AnnotationAttributes
            .fromMap(importingClassMetadata.getAnnotationAttributes(annotationType.getName(), false));
        String policy = attributes.getString("value");
        if ("A".equals(policy)) {
            return new String[] {AcontentServiceConfig.class.getName()};
        } else {
            return new String[] {BcontentServiceConfig.class.getName()};
        }
    }
}
