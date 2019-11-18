package com.cyc.demo1.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.compress.archivers.*;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.io.IOUtils;

/**
 * @author chenyuchuan
 */
public class CompressorUtil {
    private static final int IO_BUFFER = 1024 * 8;

    private static final CompressorStreamFactory COMPRESSOR_FACTORY = CompressorStreamFactory.getSingleton();

    private static final ArchiveStreamFactory ARCHIVE_FACTORY = new ArchiveStreamFactory();

    private CompressorUtil() {

    }

    public static File packageToFile(Collection<File> filesToArchive, File dir, String targetFileName,
        ArchiveType archiveType) {
        mkdir(dir);

        return packageToFile(filesToArchive, new File(dir, targetFileName), archiveType);
    }

    public static File packageToFile(Collection<File> filesToArchive, String targetFile, ArchiveType archiveType) {

        return packageToFile(filesToArchive, new File(targetFile), archiveType);
    }

    /**
     * 打包文件,如zip，tar等
     *
     * @param filesToArchive
     *            需要打包的文件
     * @param targetFile
     *            生成的打包后的文件
     * @param archiveType
     *            打包的类型
     * @return 返回打包后的文件
     */
    public static File packageToFile(Collection<File> filesToArchive, File targetFile, ArchiveType archiveType) {
        if (filesToArchive == null || archiveType == null || targetFile == null) {
            throw new IllegalArgumentException("需要打包的文件不能为null");
        }

        try (OutputStream outputStream = Files.newOutputStream(targetFile.toPath());

            ArchiveOutputStream archiveOutputStream =
                ARCHIVE_FACTORY.createArchiveOutputStream(archiveType.getArchiveType(), outputStream)) {

            filesToArchive(filesToArchive, archiveOutputStream);

            return targetFile;
        } catch (ArchiveException e) {
            throw new RuntimeException("归档文件异常:" + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("io操作异常:" + e.getMessage(), e);
        }
    }

    /**
     * 将集合中的所有文件，打包成一个tar.gz
     *
     * @param filesToArchive
     *            源文件集合
     * @param targetFile
     *            打包后的tar.gz文件
     * @return 目标文件
     */
    public static File packageToTarGzFile(Collection<File> filesToArchive, File targetFile) {
        if (filesToArchive == null) {
            throw new IllegalArgumentException("需要打包的文件不能为null");
        }

        if (targetFile == null) {
            throw new IllegalArgumentException("打包后的目标文件不能为null");
        }

        try (OutputStream outputStream = Files.newOutputStream(targetFile.toPath());

            CompressorOutputStream compressorOutputStream =
                COMPRESSOR_FACTORY.createCompressorOutputStream(CompressorStreamFactory.GZIP, outputStream);

            ArchiveOutputStream archiveOutputStream =
                ARCHIVE_FACTORY.createArchiveOutputStream(ArchiveStreamFactory.TAR, compressorOutputStream)) {

            filesToArchive(filesToArchive, archiveOutputStream);

            return targetFile;
        } catch (ArchiveException e) {
            throw new RuntimeException("归档文件异常 " + e.getMessage(), e);
        } catch (CompressorException e) {
            throw new RuntimeException("压缩文件异常 " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("io操作异常 " + e.getMessage(), e);
        }
    }

    public static File packageToTarGzFile(Collection<File> filesToArchive, String targetFile) {

        return packageToTarGzFile(filesToArchive, new File(targetFile));
    }

    public static File packageToTarGzFile(Collection<File> filesToArchive, File dir, String targetFileName) {
        mkdir(dir);

        return packageToTarGzFile(filesToArchive, new File(dir, targetFileName));
    }

    public static File packageToTarGzFile(Collection<File> filesToArchive, String dir, String targetFileName) {
        mkdir(new File(dir));

        return packageToTarGzFile(filesToArchive, new File(dir, targetFileName));
    }

    /**
     * @see CompressorUtil#unPackage(File, ArchiveType, File)
     */
    public static List<File> unPackage(String packagedFile, ArchiveType archiveType, String dir) {
        if (packagedFile == null || dir == null) {
            throw new IllegalArgumentException("输入参数不能为null");

        }

        return unPackage(new File(packagedFile), archiveType, new File(dir));
    }

    /**
     * 解包已经打包的文件，如zip，tar等
     *
     * @param packagedFile
     *            打包的文件
     * @param archiveType
     *            需要解包的类型
     * @param dir
     *            目标目录
     * @return 解包后的所有文件
     */
    public static List<File> unPackage(File packagedFile, ArchiveType archiveType, File dir) {
        checkArgsAndMkDir(packagedFile, dir);

        if (archiveType == null) {
            throw new IllegalArgumentException("输入参数不能为null");

        }

        try (InputStream inputStream = Files.newInputStream(packagedFile.toPath());

            ArchiveInputStream archiveInputStream =
                ARCHIVE_FACTORY.createArchiveInputStream(archiveType.getArchiveType(), inputStream)) {

            return resolveArchive(archiveInputStream, dir);
        } catch (ArchiveException e) {
            throw new RuntimeException("归档文件异常 " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("io操作异常 " + e.getMessage(), e);
        }
    }

    /**
     * @see CompressorUtil#unCompress(File, CompressType, File)
     */
    public static File unCompress(String compressFile, CompressType compressType, String targetFile) {
        if (compressFile == null || targetFile == null) {
            throw new IllegalArgumentException("输入参数不能为null");

        }

        return unCompress(new File(compressFile), compressType, new File(targetFile));
    }

    /**
     * 将原压缩文件，根据压缩类型，进行解压缩，生成targetFile文件
     *
     * @param compressFile
     *            压缩文件
     * @param compressType
     *            压缩文件的压缩类型
     * @param targetFile
     *            解压后的目标文件
     * @return 解压缩后的File对象
     */
    public static File unCompress(File compressFile, CompressType compressType, File targetFile) {
        checkSrcFile(compressFile);

        if (targetFile == null) {
            throw new IllegalArgumentException("targetFile不能为null");
        }

        try (InputStream inputStream = Files.newInputStream(compressFile.toPath());
            CompressorInputStream compressorInputStream =
                COMPRESSOR_FACTORY.createCompressorInputStream(compressType.getCompressType(), inputStream);
            OutputStream outputStream = Files.newOutputStream(targetFile.toPath())) {

            IOUtils.copy(compressorInputStream, outputStream, IO_BUFFER);

            return targetFile;
        } catch (CompressorException e) {
            throw new RuntimeException("压缩文件异常 " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("io操作异常 " + e.getMessage(), e);
        }
    }

    /**
     * @see CompressorUtil#compressFile(File, CompressType, File)
     */
    public static File compressFile(String unCompressFile, CompressType compressType, String compressFile) {
        if (unCompressFile == null || compressFile == null) {
            throw new IllegalArgumentException("输入参数不能为null");

        }

        return compressFile(new File(unCompressFile), compressType, new File(compressFile));
    }

    /**
     * 将原文件unCompressFile，根据压缩类型，进行压缩，生成compressFile
     * 
     * @param unCompressFile
     *            原文件
     * @param compressType
     *            压缩的类型
     * @param compressFile
     *            压缩后的文件
     * @return 压缩后的文件
     */
    public static File compressFile(File unCompressFile, CompressType compressType, File compressFile) {
        checkSrcFile(unCompressFile);

        if (compressFile == null || compressType == null) {
            throw new IllegalArgumentException("输入参数不能为null");

        }

        try (InputStream inputStream = Files.newInputStream(unCompressFile.toPath());

            OutputStream outputStream = Files.newOutputStream(compressFile.toPath());

            CompressorOutputStream compressorOutputStream =
                COMPRESSOR_FACTORY.createCompressorOutputStream(compressType.getCompressType(), outputStream)) {

            IOUtils.copy(inputStream, compressorOutputStream, IO_BUFFER);

            return compressFile;
        } catch (CompressorException e) {
            throw new RuntimeException("压缩文件异常 " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("io操作异常 " + e.getMessage(), e);
        }
    }

    /**
     * @see CompressorUtil#unCompressTarGZ(File, File)
     * @param gzFile
     *            文件的绝对路径
     * @param dir
     *            目录的绝对路径
     * @return 解压缩后的所有File对象，包含目录
     */
    public static List<File> unCompressTarGZ(String gzFile, String dir) {
        if (gzFile == null || dir == null) {
            throw new IllegalArgumentException("输入参数不能为null");
        }

        return unCompressTarGZ(new File(gzFile), new File(dir));
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
            throw new RuntimeException("io操作异常 " + e.getMessage(), e);
        }
    }

    /**
     * 解压tar.gz文件
     * 
     * @param inputStream
     *            tar.gz文件流
     * @param dir
     *            解压到的目录
     * @return 解压后的所有文件
     */
    public static List<File> unCompressTarGZ(InputStream inputStream, File dir) {
        mkdir(dir);

        try (
            CompressorInputStream compressorInputStream =
                COMPRESSOR_FACTORY.createCompressorInputStream(CompressorStreamFactory.GZIP, inputStream);

            ArchiveInputStream archiveInputStream =
                ARCHIVE_FACTORY.createArchiveInputStream(ArchiveStreamFactory.TAR, compressorInputStream)) {

            return resolveArchive(archiveInputStream, dir);
        } catch (ArchiveException e) {
            throw new RuntimeException("归档文件异常 " + e.getMessage(), e);
        } catch (CompressorException e) {
            throw new RuntimeException("压缩文件异常 " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("io操作异常 " + e.getMessage(), e);
        }
    }

    private static void filesToChildArchive(Collection<File> filesToArchive, String dirName,
        ArchiveOutputStream archiveOutputStream) throws IOException {
        for (File file : filesToArchive) {
            ArchiveEntry entry = archiveOutputStream.createArchiveEntry(file, dirName + file.getName());

            archiveOutputStream.putArchiveEntry(entry);

            if (file.isFile()) {
                try (InputStream inputStream = Files.newInputStream(file.toPath())) {
                    IOUtils.copy(inputStream, archiveOutputStream, IO_BUFFER);
                }
                archiveOutputStream.closeArchiveEntry();

            } else {
                File[] childFiles = file.listFiles();
                if (childFiles != null) {
                    filesToChildArchive(Arrays.asList(childFiles), entry.getName(), archiveOutputStream);

                }

            }

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
                archiveOutputStream.closeArchiveEntry();
            } else {
                File[] childFiles = file.listFiles();
                if (childFiles != null) {
                    filesToChildArchive(Arrays.asList(childFiles), entry.getName(), archiveOutputStream);

                }

            }

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
        checkSrcFile(srcFile);

        mkdir(dir);
    }

    private static void checkSrcFile(File srcFile) {
        if (srcFile == null) {
            throw new IllegalArgumentException("srcFile不能为null");
        }

        if (!srcFile.exists()) {
            throw new IllegalArgumentException("不存在该文件srcFile：" + srcFile.getAbsolutePath());

        }

        if (srcFile.isDirectory()) {
            throw new IllegalArgumentException("srcFile不能是目录文件");

        }
    }

    private static void mkdir(File dir) {
        if (dir == null) {
            throw new IllegalArgumentException("dir不能为null");

        }

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
