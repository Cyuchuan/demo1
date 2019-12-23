package com.cyc.demo1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cyc.demo1.entity.TestDate;
import com.cyc.demo1.entity.TestDateExample;

public interface TestDateMapper {
    int countByExample(TestDateExample example);

    int deleteByExample(TestDateExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TestDate record);

    int insertSelective(TestDate record);

    List<TestDate> selectByExample(TestDateExample example);

    TestDate selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TestDate record, @Param("example") TestDateExample example);

    int updateByExample(@Param("record") TestDate record, @Param("example") TestDateExample example);

    int updateByPrimaryKeySelective(TestDate record);

    int updateByPrimaryKey(TestDate record);
}