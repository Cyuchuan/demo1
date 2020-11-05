package com.cyc.demo1.dto;

import org.hibernate.validator.constraints.NotBlank;

import com.cyc.demo1.enumeration.FileTypeEnumeration;
import com.cyc.demo1.invoker.ServiceInterface;
import com.cyc.demo1.validation.InEnum;

import lombok.Data;
import lombok.ToString;

/**
 * @author chenyuchuan
 */
@Data
@ToString(callSuper = true)
public class Pojo1 {
    private String notValidation1;

    private String id1;

    @InEnum(inEnum = FileTypeEnumeration.class, methodName = "getValue")
    private String fileName1;

    @NotBlank(groups = ServiceInterface.class)
    private String fileType1;

    private String date1;

}
