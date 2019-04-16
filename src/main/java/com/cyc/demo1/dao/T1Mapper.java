package com.cyc.demo1.dao;

import com.cyc.demo1.entity.T1;
import com.cyc.demo1.entity.T1Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface T1Mapper {
    long countByExample(T1Example example);

    int deleteByExample(T1Example example);

    int deleteByPrimaryKey(Integer id);

    int insert(T1 record);

    int insertSelective(T1 record);

    List<T1> selectByExample(T1Example example);

    T1 selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") T1 record, @Param("example") T1Example example);

    int updateByExample(@Param("record") T1 record, @Param("example") T1Example example);

    int updateByPrimaryKeySelective(T1 record);

    int updateByPrimaryKey(T1 record);
}