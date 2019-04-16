package com.cyc.demo1.validation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Slf4j
@Component
public class ValidationUtil {
    private static Map<String, Pattern> regexMap = new ConcurrentHashMap<>(16);

    public <T> void validationField(List<T> needToValidationList) {
        // 如果list为空，则直接返回
        if (needToValidationList.isEmpty()) {
            return;
        }

        Class<?> validationPojoClass = needToValidationList.get(0).getClass();
        Field[] declaredFields = validationPojoClass.getDeclaredFields();

        HashMap<String, Field> fieldMap = new HashMap<>(32);
        HashMap<String, ValidationField> fieldValidationMap = new HashMap<>(32);

        try {
            // 先打开通道
            putFieldMapAndOpenAccess(declaredFields, fieldMap, fieldValidationMap);

            // 遍历所有对象
            for (T t : needToValidationList) {
                try {
                    // 遍历所有对象的所有字段
                    for (Field declaredField : declaredFields) {
                        ValidationField validationField = fieldValidationMap.get(declaredField.getName());

                        // 进行非空以及枚举校验、日期格式校验
                        validationNotNullAndInEnumerationAndRegex(t, declaredField, validationField);

                    }
                } catch (RuntimeException e) {
                    errorEnhance(t, fieldMap, e);
                } catch (Exception e) {
                    log.error("{}", e);
                }
            }

        } finally {
            // 关闭通道
            closeFieldAccess(declaredFields);
        }

    }

    private <T> void validationNotNullAndInEnumerationAndRegex(T t, Field declaredField,
        ValidationField validationField) throws IllegalAccessException {
        if (validationField != null && validationField.value()) {
            Object needValidationValue = declaredField.get(t);
            String fieldName = declaredField.getName();
            if (needValidationValue == null) {
                throw new RuntimeException("字段:" + fieldName + "为空");
            }

            // 进行枚举类提取，以及需要用来的枚举字段
            Class<?> enumerationClass = validationField.enumerationClass();
            String enumerationField = validationField.enumerationField();
            if (enumerationClass != Enum.class && !"".equals(enumerationField)) {
                // 用枚举中什么字段来校验
                validationEnumeration(fieldName, needValidationValue, enumerationClass, enumerationField);
            }

            String regex = validationField.regex();
            if (!"".equals(regex)) {
                // 验证日期格式
                validationPattern(fieldName, needValidationValue, regex);
            }

        }
    }

    private void validationPattern(String fieldName, Object needValidationValue, String regex) {
        Pattern validationPattern = regexMap.get(regex);

        // 如果为null,则编译当前pattern，并缓存
        if (validationPattern == null) {
            validationPattern = Pattern.compile(regex);
            regexMap.put(regex, validationPattern);
        }

        Matcher matcher = validationPattern.matcher((String)needValidationValue);

        if (!matcher.matches()) {
            throw new RuntimeException("字段:" + fieldName + ":" + needValidationValue + " 不满足日期格式 regex:" + regex);
        }
    }

    private void closeFieldAccess(Field[] declaredFields) {
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(false);
        }
    }

    private void putFieldMapAndOpenAccess(Field[] declaredFields, HashMap<String, Field> fieldMap,
        HashMap<String, ValidationField> fieldValidationMap) {
        for (Field declaredField : declaredFields) {
            fieldMap.put(declaredField.getName(), declaredField);

            ValidationField annotation = declaredField.getAnnotation(ValidationField.class);

            if (annotation != null) {
                fieldValidationMap.put(declaredField.getName(), annotation);

            }

            declaredField.setAccessible(true);
        }
    }

    private void validationEnumeration(String fieldName, Object needValidationValue, Class<?> enumerationClass,
        String enumerationField) {
        Field enumerationClassField;
        try {
            enumerationClassField = enumerationClass.getDeclaredField(enumerationField);
        } catch (Exception e) {
            throw new RuntimeException(enumerationClass + " 不存在属性 " + enumerationField);
        }

        try {
            enumerationClassField.setAccessible(true);

            Method values = enumerationClass.getMethod("values");
            Enum[] enums = (Enum[])(values.invoke(enumerationClass));

            boolean needToThrow = true;
            for (Enum anEnum : enums) {
                // 当前枚举字段的值
                Object enumerationFieldValue = enumerationClassField.get(anEnum);

                if (enumerationFieldValue.equals(needValidationValue)) {
                    needToThrow = false;

                    break;
                }

            }

            if (needToThrow) {
                throw new RuntimeException(
                    "字段:" + fieldName + ":" + needValidationValue + " 不满足枚举 " + enumerationClass);

            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        } finally {
            enumerationClassField.setAccessible(false);

        }
    }

    private <T> void errorEnhance(T t, HashMap<String, Field> fieldMap, RuntimeException e) {
        Class<?> validationPojoClass = t.getClass();
        ErrorPrefixField errorPrefixField =
            (ErrorPrefixField)(validationPojoClass.getAnnotation(ErrorPrefixField.class));
        if (errorPrefixField == null) {
            throw e;

        }

        String[] prefixFields = errorPrefixField.value();
        if (prefixFields.length == 0) {
            throw e;

        }

        StringBuilder errorBuilder = new StringBuilder(32);
        try {
            for (String prefixField : prefixFields) {
                Field field = fieldMap.get(prefixField);

                if (field == null) {
                    throw new RuntimeException(
                        errorPrefixField + " 定义的 " + prefixField + " 字段在 " + validationPojoClass + " 中不存在");
                }

                Object value = field.get(t);
                errorBuilder.append(field.getName()).append(":").append(value).append(" ");
            }
        } catch (IllegalAccessException e1) {
            throw new RuntimeException(e1.getMessage());
        }

        errorBuilder.append(e.getMessage());

        throw new RuntimeException(errorBuilder.toString());
    }

    public void test() {

        ArrayList<? super Number> objects = new ArrayList<>();
        objects.add(1);

        ArrayList<Integer> objects1 = new ArrayList<>();
        Integer integer = new Integer(1);
        objects1.add(integer);

        log.error("{}", objects);
    }
}
