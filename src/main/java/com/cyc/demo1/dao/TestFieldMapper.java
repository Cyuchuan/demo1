package com.cyc.demo1.dao;

import com.cyc.demo1.entity.TestField;
import com.cyc.demo1.entity.TestFieldExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestFieldMapper {
    long countByExample(TestFieldExample example);

    int deleteByExample(TestFieldExample example);

    int insert(TestField record);

    int insertSelective(TestField record);

    List<TestField> selectByExample(TestFieldExample example);

    int updateByExampleSelective(@Param("record") TestField record, @Param("example") TestFieldExample example);

    int updateByExample(@Param("record") TestField record, @Param("example") TestFieldExample example);
}