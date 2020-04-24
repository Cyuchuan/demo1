package com.cyc.demo1;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.NumberUtils;

import com.cyc.demo1.dto.Result;

/**
 * @author atchen
 */
public class ResultEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String[] split = StringUtils.split(text, ",");
        Result<Object> objectResult = new Result<>();

        Integer integer = NumberUtils.parseNumber(split[0], Integer.class);
        objectResult.setCode(integer);
        objectResult.setMessage(split[1]);
        setValue(objectResult);
    }
}
