package com.cyc.demo1.util;

/**
 * 打包的类型
 * 
 * @author chenyuchuan
 */
public enum ArchiveType {
    TAR("tar"), zip("zip"), TO7Z("7z"), JAR("jar");

    private String archiveType;

    ArchiveType(String archiveType) {
        this.archiveType = archiveType;
    }

    public String getArchiveType() {
        return archiveType;
    }
}
