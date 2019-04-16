package com.cyc.demo1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 正则表达式匹配
 * 
 * @author chenyuchuan
 */
@Slf4j
public class RegexTest {

    private int i;

    @Test
    public void regexTest() {
        String str = "404937230@qq.com";

        Assert.assertTrue("这是错误的", str.matches("\\d*@qq\\.com"));
    }

    @Test
    public void test1() {
        String a = "11231", b = "22334234";
        String[] ab = {a, b};
        log.error("{}", ab);
    }

    @Test
    public void test2() {

        int j = ++this.i;
        log.error("{}   {}  {}", i, j, ++i == j);

    }

    @Test
    public void test3() {
        String s = "aaaaaaaa";
        Pattern compile = Pattern.compile("a+?");

        Matcher matcher = compile.matcher(s);
        while (matcher.find()) {
            String group = matcher.group();
        }
    }
}
