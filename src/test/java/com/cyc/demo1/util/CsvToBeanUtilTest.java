package com.cyc.demo1.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.cyc.demo1.pojo.Person;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CsvToBeanUtilTest {

    @Test
    public void toBeanList() {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("person.csv");

        // 直接获取整个文件内容，转化为对象
        List<Person> personList =
            CsvToBeanUtil.toBeanList(resourceAsStream, StandardCharsets.UTF_8, "@!@", Person.class);

        log.error("大小：{}  值：{}", personList.size(), personList.get(0));

        String fileString = this.getClass().getClassLoader().getResource("person.csv").getFile();
        File file = FileUtils.getFile(fileString);

        // 直接获取整个文件内容，转化为对象
        List<Person> personList1 = CsvToBeanUtil.toBeanList(file, StandardCharsets.UTF_8, "@!@", Person.class);
        log.error("大小：{}  值：{}", personList.size(), personList.get(0));

    }

    @Test
    public void toCanProcessFile() {
        String file = this.getClass().getClassLoader().getResource("dd6afb53-1d98-43a3-a1dc-199e3bd5609b").getFile();

        File file1 = CsvToBeanUtil.toCanProcessFile(new File(file), StandardCharsets.UTF_8,
            CsvToBeanUtil.DEFAULT_SEPARATOR_STR, "!000000000000000!");
        log.error("{}", file1);

    }

    @Test
    public void toBeanIterator() {
        String file = this.getClass().getClassLoader().getResource("person.csv").getFile();
        final File srcFile = FileUtils.getFile(file);

        final File tempFile = CsvToBeanUtil.toCanProcessCsvFile(srcFile, StandardCharsets.UTF_8, "@!@");

        // 打开临时文件流传入，在try-with-resource中完成业务处理，java在结束时，自己会进行流的关闭
        try (FileInputStream tempFileInputStream = FileUtils.openInputStream(tempFile)) {
            Iterator<Person> personIterator =
                CsvToBeanUtil.toBeanIterator(tempFileInputStream, StandardCharsets.UTF_8, Person.class);

            // 业务处理
            int sum = 0;
            Person next = null;
            while (personIterator.hasNext()) {
                next = personIterator.next();
                sum++;
            }
            log.error("大小：{}  值：{}", sum, next);
        } catch (IOException e) {

        } finally {
            // 删除临时文件
            FileUtils.deleteQuietly(tempFile);

        }

    }

    @Test
    public void writeBeansToFile() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Person person = new Person();
            person.setAge(ThreadLocalRandom.current().nextInt(100));
            person.setName(UUID.randomUUID().toString());
            person.setBrithday(new Date());

            personList.add(person);
        }

        CsvToBeanUtil.writeBeansToFile(personList, new File("ceshi"), StandardCharsets.UTF_8, "@!@");
    }

}