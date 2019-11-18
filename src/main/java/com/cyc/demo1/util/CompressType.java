package com.cyc.demo1.util;

/**
 * 压缩格式
 * 
 * @author chenyuchuan
 */
public enum CompressType {
    GZ("gz"), BZIP2("bzip2"),;

    private String compressType;

    CompressType(String compressType) {
        this.compressType = compressType;
    }

    public String getCompressType() {
        return compressType;
    }
}
