package com.cyc.demo1.util;

import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class CompressorUtilTest {

    @Test
    public void packageToFile() {
        File file = new File(".");
        File[] files = file.listFiles();
        List<File> fileList = Arrays.asList(files);

        File zip = CompressorUtil.packageToFile(fileList, "packageToFile.zip", ArchiveType.zip);
        assertThat(zip.getName(), CoreMatchers.equalTo("packageToFile.zip"));
    }

    @Test
    public void packageToTarGzFile() {
        File file = new File(".");
        File[] files = file.listFiles();
        List<File> fileList = Arrays.asList(files);

        File tarGzFile = CompressorUtil.packageToTarGzFile(fileList, "packageToFile.tar.gz");
        assertThat(tarGzFile.getName(), CoreMatchers.equalTo("packageToFile.tar.gz"));

    }

    @Test
    public void unPackage() {
        List<File> files = CompressorUtil.unPackage("packageToFile.zip", ArchiveType.zip, "unzip");
        assertThat(files.get(0).getParentFile().getName(), CoreMatchers.equalTo("unzip"));

    }

    @Test
    public void unCompressTarGZ() {
        List<File> files = CompressorUtil.unCompressTarGZ(new File("packageToFile.tar.gz"), new File("untargz"));
        assertThat(files.get(0).getParentFile().getName(), CoreMatchers.equalTo("untargz"));

    }

    @Test
    public void compressFile() {
        String fileString = this.getClass().getClassLoader().getResource("person.csv").getFile();
        File file = FileUtils.getFile(fileString);

        File compressFile = CompressorUtil.compressFile(file, CompressType.GZ, new File("person.gz"));

    }

    @Test
    public void unCompress() {
        File zip = CompressorUtil.unCompress(new File("person.gz"), CompressType.GZ, new File("person.csv"));
    }

}