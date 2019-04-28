package com.cyc.demo1.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Slf4j
public class FileUtil {
    private FileUtil() {

    }

    public static File getFile(File directory, String... names) {
        return FileUtils.getFile(directory, names);
    }

    public static File getFile(final String... names) {
        return FileUtils.getFile(names);
    }

    public static FileInputStream openInputStream(File file) {
        try {
            return FileUtils.openInputStream(file);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
