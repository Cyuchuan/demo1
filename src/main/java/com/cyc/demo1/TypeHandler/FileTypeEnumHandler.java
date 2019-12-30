package com.cyc.demo1.TypeHandler;

import com.cyc.demo1.enumeration.FileTypeEnumeration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author atchen
 */
public class FileTypeEnumHandler implements TypeHandler<FileTypeEnumeration> {

    @Override
    public void setParameter(PreparedStatement ps, int i, FileTypeEnumeration parameter, JdbcType jdbcType)
        throws SQLException {
        // 设置prepareStatement的入参
        ps.setString(i, parameter.getValue());
    }

    @Override
    public FileTypeEnumeration getResult(ResultSet rs, String columnName) throws SQLException {
        String string = rs.getString(columnName);

        return FileTypeEnumeration.getEnumByValue(string);

    }

    @Override
    public FileTypeEnumeration getResult(ResultSet rs, int columnIndex) throws SQLException {
        String string = rs.getString(columnIndex);

        return FileTypeEnumeration.getEnumByValue(string);
    }

    @Override
    public FileTypeEnumeration getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String string = cs.getString(columnIndex);

        return FileTypeEnumeration.getEnumByValue(string);
    }
}
