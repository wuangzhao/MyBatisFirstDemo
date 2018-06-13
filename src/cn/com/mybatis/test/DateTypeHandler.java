package cn.com.mybatis.test;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;
import java.text.SimpleDateFormat;

public class DateTypeHandler implements TypeHandler<Date> {

    //转换日期类型的辅助类
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Date date, JdbcType jdbcType) throws SQLException {
        System.out.println("其他逻辑");
        preparedStatement.setDate(i, date);
        System.out.println("其他逻辑");
    }

    @Override
    public Date getResult(ResultSet resultSet, String s) throws SQLException {
        System.out.println("其他逻辑");
        return null;
    }

    @Override
    public Date getResult(ResultSet resultSet, int i) throws SQLException {
        System.out.println("其他逻辑");
        return resultSet.getDate(i);
    }

    @Override
    public Date getResult(CallableStatement callableStatement, int i) throws SQLException {
        System.out.println("其他逻辑");
        return callableStatement.getDate(i);
    }
}
