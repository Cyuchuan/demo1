package com.cyc.demo1.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.io.IOUtils;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.util.Assert;

/**
 * @author chenyuchuan
 */
@Slf4j
public class CompressorUtil {

    private static final CompressorStreamFactory COMPRESSOR_FACTORY = CompressorStreamFactory.getSingleton();

    private static final ArchiveStreamFactory ARCHIVE_FACTORY = new ArchiveStreamFactory();

    private CompressorUtil() {

    }

    /**
     * 解包已经打包的文件，如zip，tar等
     * 
     * @param packagedFile
     *            打包的文件
     * @param dir
     *            目标目录
     * @return 解包后的所有File对象，包含目录
     */
    public static List<File> unPackage(File packagedFile, File dir) {
        checkArgsAndMkDir(packagedFile, dir);

        String packageType = getSrcFileType(packagedFile);

        try (InputStream inputStream = Files.newInputStream(packagedFile.toPath());

            ArchiveInputStream archiveInputStream =
                ARCHIVE_FACTORY.createArchiveInputStream(packageType, inputStream)) {

            return resolveArchive(packagedFile, dir, archiveInputStream);
        } catch (ArchiveException e) {
            log.error("{}", e);
            throw new RuntimeException("归档文件异常 " + e.getMessage());
        } catch (IOException e) {
            log.error("{}", e);
            throw new RuntimeException("io操作异常 " + e.getMessage());
        }
    }

    /**
     * 解压缩压缩后的文件，如gz等
     * 
     * @param compressFile
     *            压缩文件
     * @param dir
     *            目标目录
     * @return 解压缩后的所有File对象，包含目录
     */
    public static List<File> unCompress(File compressFile, File dir) {
        checkArgsAndMkDir(compressFile, dir);

        String simpleName = getSrcFileSimpleName(compressFile);
        String compressType = getSrcFileType(compressFile);

        List<File> unPackageFiles = new ArrayList<>(10);

        try (InputStream inputStream = Files.newInputStream(compressFile.toPath());

            CompressorInputStream compressorInputStream =
                COMPRESSOR_FACTORY.createCompressorInputStream(compressType, inputStream)) {

            File target = new File(dir, simpleName);
            try (OutputStream outputStream = Files.newOutputStream(target.toPath())) {
                IOUtils.copy(compressorInputStream, outputStream);
            }

            unPackageFiles.add(target);
            return unPackageFiles;
        } catch (CompressorException e) {
            log.error("{}", e);
            throw new RuntimeException("压缩文件异常 " + e.getMessage());
        } catch (IOException e) {
            log.error("{}", e);
            throw new RuntimeException("io操作异常 " + e.getMessage());
        }
    }

    /**
     * 解压tar.gz文件
     * 
     * @param gzFile
     *            tar.gz 文件
     * @param dir
     *            目标目录
     * @return 解压缩后的所有File对象，包含目录
     */
    public static List<File> unCompressTarGZ(File gzFile, File dir) {
        checkArgsAndMkDir(gzFile, dir);

        try (InputStream inputStream = Files.newInputStream(gzFile.toPath());

            CompressorInputStream compressorInputStream =
                COMPRESSOR_FACTORY.createCompressorInputStream(CompressorStreamFactory.GZIP, inputStream);

            ArchiveInputStream archiveInputStream =
                ARCHIVE_FACTORY.createArchiveInputStream(ArchiveStreamFactory.TAR, compressorInputStream)) {

            return resolveArchive(gzFile, dir, archiveInputStream);
        } catch (ArchiveException e) {
            log.error("{}", e);
            throw new RuntimeException("归档文件异常 " + e.getMessage());
        } catch (CompressorException e) {
            log.error("{}", e);
            throw new RuntimeException("压缩文件异常 " + e.getMessage());
        } catch (IOException e) {
            log.error("{}", e);
            throw new RuntimeException("io操作异常 " + e.getMessage());
        }
    }

    private static List<File> resolveArchive(File srcFile, File dir, ArchiveInputStream archiveInputStream)
        throws IOException {
        List<File> resolveFiles = new ArrayList<>(10);

        ArchiveEntry entry;
        while ((entry = archiveInputStream.getNextEntry()) != null) {
            if (!archiveInputStream.canReadEntryData(entry)) {
                throw new RuntimeException("归档文件：" + srcFile.getAbsolutePath() + " 不可读");
            }

            // 将写入的文件
            File entryToFile = entryToFile(dir, entry);

            // entry是目录
            if (entry.isDirectory()) {
                if (!entryToFile.exists() && !entryToFile.mkdirs()) {
                    throw new RuntimeException("目录entryToFile：" + entryToFile.getAbsolutePath() + " 无法创建");
                }

            } else {
                // entry是文件
                try (OutputStream outputStream = Files.newOutputStream(entryToFile.toPath())) {
                    IOUtils.copy(archiveInputStream, outputStream);
                }
            }

            resolveFiles.add(entryToFile);
        }
        return resolveFiles;
    }

    private static String getSrcFileType(File srcFile) {
        String name = srcFile.getName();
        int lastPoint = name.lastIndexOf('.');

        try {
            return name.substring(lastPoint + 1);
        } catch (Exception e) {
            throw new RuntimeException("文件后缀不正确：" + srcFile.getName() + " 无法确定是什么文件类型");
        }
    }

    private static String getSrcFileSimpleName(File srcFile) {
        String name = srcFile.getName();
        int lastPoint = name.lastIndexOf('.');

        try {
            return name.substring(0, lastPoint);
        } catch (Exception e) {
            throw new RuntimeException("文件后缀不正确：" + srcFile.getName() + " 无法确定是什么文件类型");
        }
    }

    private static void checkArgsAndMkDir(File srcFile, File dir) {
        Assert.notNull(srcFile, "srcFile不能为null");
        Assert.notNull(dir, "dir不能为null");

        if (!srcFile.exists()) {
            throw new IllegalArgumentException("不存在该文件srcFile：" + srcFile.getAbsolutePath());

        }

        if (srcFile.isDirectory()) {
            throw new IllegalArgumentException("srcFile不能是目录文件");

        }

        if (dir.isFile()) {
            throw new IllegalArgumentException("dir不能是文件");

        }

        // 通过了以上检查说明参数正确
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException("目录dir：" + dir.getAbsolutePath() + " 无法创建");
            }
        }
    }

    private static File entryToFile(File dir, ArchiveEntry entry) {
        String name = entry.getName();
        return new File(dir, name);

    }
}
