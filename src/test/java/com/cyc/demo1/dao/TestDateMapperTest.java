package com.cyc.demo1.dao;

import java.util.Date;

import com.cyc.demo1.enumeration.FileTypeEnumeration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cyc.demo1.entity.TestDate;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestDateMapperTest {
    @Autowired
    private TestDateMapper testDateMapper;

    @Test
    public void insert() {
        TestDate testDate = new TestDate();
        testDate.setId(2L);
        testDate.setRevision(0);
        testDate.setCreatedBy("");
        testDate.setCreatedTime(new Date());
        testDate.setUpdatedBy("");
        testDate.setUpdatedTime(new Date());
        testDate.setTime(new Date());
        testDate.setDate(new Date());
        testDate.setDatetime(new Date());
        testDate.setFileTypeEnumeration(FileTypeEnumeration.ZIBENZHUYI);
        testDateMapper.insert(testDate);
    }

    @Test
    public void selectByPrimaryKey() {
        TestDate testDate = testDateMapper.selectByPrimaryKey(1L);
        log.error("{}", testDate);

    }
}