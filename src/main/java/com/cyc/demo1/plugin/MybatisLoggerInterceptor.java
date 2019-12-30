package com.cyc.demo1.plugin;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;

@Intercepts(@Signature(args = {Connection.class, Integer.class}, method = "prepare", type = StatementHandler.class))
public class MybatisLoggerInterceptor implements Interceptor {
    /**
     * @param arg0
     * @return
     * @throws Throwable
     * @describe 拦截mybatis方法，输出执行sql信息
     */
    @Override
    public Object intercept(Invocation arg0) throws Throwable {
        RoutingStatementHandler handler = (RoutingStatementHandler)arg0.getTarget();
        BoundSql boundSql = handler.getBoundSql();
        String sql = replaceStr(boundSql.getSql(), new String[] {"\n", "\t", "\r"}, " ");
        // System.out.println(sql);
        return arg0.proceed();
    }

    /**
     * @param arg0
     * @return
     * @describe 将方法进行包裹
     */
    @Override
    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }

    /**
     * @param arg0
     * @describe 获取拦截配置初始化参数
     */
    @Override
    public void setProperties(Properties arg0) {}

    /**
     * @param str
     * @param oldStrs
     * @param newStr
     * @return
     * @describe 字符串中，指定多个字符由新字符替换
     */
    public static String replaceStr(String str, String[] oldStrs, String newStr) {
        String retStr = str;
        if (str != null && !"".equals(str) && oldStrs.length > 0) {
            for (int i = 0; i < oldStrs.length; i++) {
                if (oldStrs[i] != null && !"".equals(oldStrs[i])) {
                    retStr = retStr.replaceAll(oldStrs[i], newStr);
                }
            }
        }
        return retStr;
    }
}
