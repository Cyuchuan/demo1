package com.cyc.demo1;

import java.util.ArrayList;

import org.junit.Test;

import com.cyc.demo1.dto.Pojo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Slf4j
public class Vaildation {
    @Test
    public void test() throws Exception {
        ArrayList<Pojo> objects = new ArrayList<>();

        Pojo pojo = new Pojo();
        pojo.setId("1111");
        pojo.setFileName("1321");
        pojo.setFileType("3");
        pojo.setFileName("20180205");
        pojo.setDate("20181231");

        objects.add(pojo);

        long startTime = System.currentTimeMillis();
        // ValidationUtil.validationField(objects);

        log.error("耗时为{}", System.currentTimeMillis() - startTime);
    }

    // @Test
    // public void fanxingTest() {
    // ValidationUtil.test();
    // }
}
