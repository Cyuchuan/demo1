package com.cyc.demo1.util;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CompressorUtilTest {

    @Test
    public void packageToFile() {
        File file = new File(".");
        File[] files = file.listFiles();
        List<File> fileList = Arrays.asList(files);

        File zip = CompressorUtil.packageToFile(fileList, "packageToFile.zip");
    }

    @Test
    public void packageToTarGzFile() {
        File file = new File(".");
        File[] files = file.listFiles();
        List<File> fileList = Arrays.asList(files);

        File tarGzFile = CompressorUtil.packageToTarGzFile(fileList, "packageToFile.tar.gz");
    }

    @Test
    public void unPackage() {
        List<File> files = CompressorUtil.unPackage(new File("packageToFile.zip"), new File("./unzip"));
    }


    @Test
    public void unCompressTarGZ() {
        List<File> files = CompressorUtil.unCompressTarGZ(new File("packageToFile.tar.gz"), new File("./untargz"));

    }


}