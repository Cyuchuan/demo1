package com.cyc.demo1.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.compress.archivers.*;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author chenyuchuan
 */
public class CompressorUtil {
    public static final Logger log = LoggerFactory.getLogger(CompressorUtil.class);

    private static final int IO_BUFFER = 1024 * 8;

    private static final CompressorStreamFactory COMPRESSOR_FACTORY = CompressorStreamFactory.getSingleton();

    private static final ArchiveStreamFactory ARCHIVE_FACTORY = new ArchiveStreamFactory();

    private CompressorUtil() {

    }

    public static File packageToFile(Collection<File> filesToArchive, File dir, String targetFileName) {
        mkdir(dir);

        return packageToFile(filesToArchive, new File(dir, targetFileName));
    }

    public static File packageToFile(Collection<File> filesToArchive, String targetFileName) {

        return packageToFile(filesToArchive, new File(targetFileName));
    }

    /**
     * 打包文件,如zip，tar等
     *
     * @param filesToArchive
     *            需要打包的文件
     * @param targetFile
     *            生成的打包后的文件
     * @return 返回打包后的文件
     */
    public static File packageToFile(Collection<File> filesToArchive, File targetFile) {
        Assert.notNull(filesToArchive, "需要打包的文件不能为null");
        Assert.notNull(targetFile, "打包后的目标文件不能为null");

        String fileType = getFileType(targetFile);

        try (OutputStream outputStream = Files.newOutputStream(targetFile.toPath());

            ArchiveOutputStream archiveOutputStream =
                ARCHIVE_FACTORY.createArchiveOutputStream(fileType, outputStream)) {

            filesToArchive(filesToArchive, archiveOutputStream);

            return targetFile;
        } catch (ArchiveException e) {
            log.error("{}", e);
            throw new RuntimeException("归档文件异常 " + e.getMessage());
        } catch (IOException e) {
            log.error("{}", e);
            throw new RuntimeException("io操作异常 " + e.getMessage());
        }
    }

    /**
     * 打包成tar.gz
     *
     * @param filesToArchive
     *            源文件
     * @param targetFile
     *            目标文件
     * @return 目标文件
     */
    public static File packageToTarGzFile(Collection<File> filesToArchive, File targetFile) {
        Assert.notNull(filesToArchive, "需要打包的文件不能为null");
        Assert.notNull(targetFile, "打包后的目标文件不能为null");

        try (OutputStream outputStream = Files.newOutputStream(targetFile.toPath());

            CompressorOutputStream compressorOutputStream =
                COMPRESSOR_FACTORY.createCompressorOutputStream(CompressorStreamFactory.GZIP, outputStream);

            ArchiveOutputStream archiveOutputStream =
                ARCHIVE_FACTORY.createArchiveOutputStream(ArchiveStreamFactory.TAR, compressorOutputStream)) {

            filesToArchive(filesToArchive, archiveOutputStream);

            return targetFile;
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

    public static File packageToTarGzFile(Collection<File> filesToArchive, String targetFileName) {

        return packageToTarGzFile(filesToArchive, new File(targetFileName));
    }

    public static File packageToTarGzFile(Collection<File> filesToArchive, File dir, String targetFileName) {
        mkdir(dir);

        return packageToTarGzFile(filesToArchive, new File(dir, targetFileName));
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

        String packageType = getFileType(packagedFile);

        try (InputStream inputStream = Files.newInputStream(packagedFile.toPath());

            ArchiveInputStream archiveInputStream =
                ARCHIVE_FACTORY.createArchiveInputStream(packageType, inputStream)) {

            return resolveArchive(archiveInputStream, dir);
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

        String simpleName = getFileSimpleName(compressFile);
        String compressType = getFileType(compressFile);

        List<File> unPackageFiles = new ArrayList<>(10);

        try (InputStream inputStream = Files.newInputStream(compressFile.toPath());

            CompressorInputStream compressorInputStream =
                COMPRESSOR_FACTORY.createCompressorInputStream(compressType, inputStream)) {

            File target = new File(dir, simpleName);
            try (OutputStream outputStream = Files.newOutputStream(target.toPath())) {
                IOUtils.copy(compressorInputStream, outputStream, IO_BUFFER);
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

        try (InputStream inputStream = Files.newInputStream(gzFile.toPath())) {

            return unCompressTarGZ(inputStream, dir);
        } catch (IOException e) {
            log.error("{}", e);
            throw new RuntimeException("io操作异常 " + e.getMessage());
        }
    }

    public static List<File> unCompressTarGZ(InputStream inputStream, File dir) {
        mkdir(dir);

        try (
            CompressorInputStream compressorInputStream =
                COMPRESSOR_FACTORY.createCompressorInputStream(CompressorStreamFactory.GZIP, inputStream);

            ArchiveInputStream archiveInputStream =
                ARCHIVE_FACTORY.createArchiveInputStream(ArchiveStreamFactory.TAR, compressorInputStream)) {

            return resolveArchive(archiveInputStream, dir);
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

    private static void filesToArchive(Collection<File> filesToArchive, ArchiveOutputStream archiveOutputStream)
        throws IOException {
        for (File file : filesToArchive) {
            ArchiveEntry entry = archiveOutputStream.createArchiveEntry(file, file.getName());

            archiveOutputStream.putArchiveEntry(entry);

            if (file.isFile()) {
                try (InputStream inputStream = Files.newInputStream(file.toPath())) {
                    IOUtils.copy(inputStream, archiveOutputStream, IO_BUFFER);
                }
            }

            archiveOutputStream.closeArchiveEntry();
        }

        archiveOutputStream.finish();
    }

    private static List<File> resolveArchive(ArchiveInputStream archiveInputStream, File dir) throws IOException {
        List<File> resolveFiles = new ArrayList<>(10);

        ArchiveEntry entry;
        while ((entry = archiveInputStream.getNextEntry()) != null) {
            if (!archiveInputStream.canReadEntryData(entry)) {
                throw new RuntimeException("归档文件不可读");
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
                    IOUtils.copy(archiveInputStream, outputStream, IO_BUFFER);
                }
            }

            resolveFiles.add(entryToFile);
        }
        return resolveFiles;
    }

    private static String getFileType(File file) {
        String name = file.getName();
        int lastPoint = name.lastIndexOf('.');

        try {
            return name.substring(lastPoint + 1);
        } catch (Exception e) {
            throw new RuntimeException("文件后缀不正确：" + file.getName() + " 无法确定是什么文件类型");
        }
    }

    private static String getFileSimpleName(File file) {
        String name = file.getName();
        int lastPoint = name.lastIndexOf('.');

        try {
            return name.substring(0, lastPoint);
        } catch (Exception e) {
            throw new RuntimeException("文件后缀不正确：" + file.getName() + " 无法确定是什么文件类型");
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

        mkdir(dir);
    }

    private static void mkdir(File dir) {
        if (dir.isFile()) {
            throw new IllegalArgumentException("dir不能是文件");

        }

        // 通过了以上检查说明参数正确
        if (!dir.exists() && !dir.mkdirs()) {
            throw new RuntimeException("目录dir：" + dir.getAbsolutePath() + " 无法创建");
        }
    }

    private static File entryToFile(File dir, ArchiveEntry entry) {
        String name = entry.getName();
        return new File(dir, name);

    }
}
