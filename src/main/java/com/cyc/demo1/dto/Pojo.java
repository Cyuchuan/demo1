package com.cyc.demo1.dto;

import java.lang.invoke.MethodHandles;

import javax.validation.constraints.NotBlank;

import com.cyc.demo1.enumeration.FileTypeEnumeration;
import com.cyc.demo1.validation.ErrorPrefixField;
import com.cyc.demo1.validation.ValidationField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenyuchuan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ErrorPrefixField({"id", "fileName"})
public class Pojo {
    @NotBlank
    private String notValidation;

    @ValidationField
    @NotBlank
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
