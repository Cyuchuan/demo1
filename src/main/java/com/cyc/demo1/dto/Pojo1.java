package com.cyc.demo1.dto;

import com.cyc.demo1.validation.ErrorPrefixField;
import lombok.Data;
import lombok.ToString;

/**
 * @author chenyuchuan
 */
@Data
@ToString(callSuper = true)
@ErrorPrefixField({"id", "fileName"})
public class Pojo1 extends Pojo{
    private String notValidation1;

    private String id1;

    private String fileName1;

    private String fileType1;

    private String date1;

}
