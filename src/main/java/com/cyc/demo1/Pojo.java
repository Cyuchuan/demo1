package com.cyc.demo1;

import java.lang.invoke.MethodHandles;

import lombok.Data;

/**
 * @author chenyuchuan
 */
@Data
@ErrorPrefixField({"id", "fileName"})
public class Pojo {
    private String notValidation;

    @ValidationField
    private String id;

    @ValidationField
    private String fileName;

    @ValidationField(enumerationClass = FileTypeEnumeration.class, enumerationField = "value")
    private String fileType;

    @ValidationField(regex = "^\\d{4}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$")
    private String date;

    public static MethodHandles.Lookup getLookup() {
        return MethodHandles.lookup();
    }

}
