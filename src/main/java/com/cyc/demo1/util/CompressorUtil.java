package com.cyc.demo1.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author chenyuchuan
 */
public class CompressorUtil {
    private static final int BUFFER_SIZE = 1024;

    private static final CompressorStreamFactory COMPRESSOR_STREAM_FACTORY = CompressorStreamFactory.getSingleton();

    private CompressorUtil() {

    }

    public static void zipDecompress(InputStream inputStream, String dirPath) {
        try {
            CompressorInputStream compressorInputStream =
                COMPRESSOR_STREAM_FACTORY.createCompressorInputStream(inputStream);

            File file = new File("testZip/");
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            byte[] buffer = new byte[4096];
            int len = -1;
            while ((len = compressorInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }

            compressorInputStream.close();
            fileOutputStream.close();

        } catch (CompressorException e) {
            throw new RuntimeException("解压失败：" + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("解压失败：" + e.getMessage());
        }

    }

    public static List<String> unZip(File zipFile, String destDir) throws Exception {
        // 如果 destDir 为 null, 空字符串, 或者全是空格, 则解压到压缩文件所在目录
        if (StringUtils.isBlank(destDir)) {
            destDir = zipFile.getParent();
        }

        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        File dir = new File(destDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        ZipArchiveInputStream is = null;
        List<String> fileNames = new ArrayList<String>();

        try {
            is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipFile), BUFFER_SIZE));
            ZipArchiveEntry entry = null;

            while ((entry = is.getNextZipEntry()) != null) {
                fileNames.add(entry.getName());

                if (entry.isDirectory()) {
                    File directory = new File(destDir, entry.getName());
                    directory.mkdirs();
                } else {
                    OutputStream os = null;
                    File tempFile = new File(destDir, entry.getName());

                    if (!tempFile.exists()) {
                        tempFile.createNewFile();
                    }

                    try {
                        os = new BufferedOutputStream(new FileOutputStream(tempFile), BUFFER_SIZE);
                        IOUtils.copy(is, os);
                    } finally {
                        IOUtils.closeQuietly(os);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            IOUtils.closeQuietly(is);
        }

        return fileNames;
    }

}
