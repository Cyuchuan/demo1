package com.cyc.demo1.enumeration;

import lombok.Getter;

@Getter
public enum FileTypeEnumeration {
    SHEHUIZHUYI("1", "社会主义"), ZIBENZHUYI("2", "资本主义");

    private String value;
    private String desc;

    FileTypeEnumeration(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static FileTypeEnumeration getEnumByValue(String value) {
        FileTypeEnumeration[] values = values();
        for (FileTypeEnumeration fileTypeEnumeration : values) {
            if (fileTypeEnumeration.getValue().equals(value)) {
                return fileTypeEnumeration;
            }
        }
        return null;
    }
}
