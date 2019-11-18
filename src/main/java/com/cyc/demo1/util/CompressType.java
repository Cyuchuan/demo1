package com.cyc.demo1.util;

/**
 * 压缩格式
 * 
 * @author chenyuchuan
 */
public enum CompressType {
    GZ("gz"), BZIP2("bzip2"), PACK200("pack200"), DEFLATE("deflate"), LZ4_BLOCK("lz4-block"), LZ4_FRAMED("lz4-framed"),
    SNAPPY_FRAMED("snappy-framed"),;

    private String compressType;

    CompressType(String compressType) {
        this.compressType = compressType;
    }

    public String getCompressType() {
        return compressType;
    }
}
