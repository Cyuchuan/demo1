package com.cyc.demo1.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.io.FileUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.cyc.demo1.pojo.Person;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CsvToBeanUtilTest {

    @Test
    public void toBeanList() {
        List<Person> personList = null;
        try (InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("person.csv")) {

            // 直接获取整个文件内容，转化为对象
            personList = CsvToBeanUtil.toBeanList(resourceAsStream, StandardCharsets.UTF_8, "@!@", Person.class);
        } catch (IOException e) {

        }

        Assert.assertThat(personList.size(), CoreMatchers.equalTo(30750));
        Person person = Person.builder().name("小明").age(18).build();
        Assert.assertThat(personList.get(0), CoreMatchers.equalTo(person));

        String fileString = this.getClass().getClassLoader().getResource("person.csv").getFile();
        File file = FileUtils.getFile(fileString);

        // 直接获取整个文件内容，转化为对象
        personList = CsvToBeanUtil.toBeanList(file, StandardCharsets.UTF_8, "@!@", Person.class);
        Assert.assertThat(personList.size(), CoreMatchers.equalTo(30750));
        Assert.assertThat(personList.get(0), CoreMatchers.equalTo(person));

        // 带引号的文件
        try (InputStream resourceAsStream =
            this.getClass().getClassLoader().getResourceAsStream("person-withQuote.csv")) {

            // 直接获取整个文件内容，转化为对象
            personList = CsvToBeanUtil.toBeanList(resourceAsStream, StandardCharsets.UTF_8, "@!@", Person.class, true);
        } catch (IOException e) {

        }

        Assert.assertThat(personList.size(), CoreMatchers.equalTo(1000));
        LocalDate of = LocalDate.of(2019, 11, 17);
        Date from = Date.from(of.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        person = Person.builder().name("\\a").age(50).brithday(from).build();
        Assert.assertThat(personList.get(0), CoreMatchers.equalTo(person));

        String fileStringWithQuote = this.getClass().getClassLoader().getResource("person-withQuote.csv").getFile();
        File fileWithQuote = FileUtils.getFile(fileStringWithQuote);

        // 直接获取整个文件内容，转化为对象
        personList = CsvToBeanUtil.toBeanList(fileWithQuote, StandardCharsets.UTF_8, "@!@", Person.class, true);
        Assert.assertThat(personList.size(), CoreMatchers.equalTo(1000));
        Assert.assertThat(personList.get(0), CoreMatchers.equalTo(person));
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
                sum++;
                next = personIterator.next();
            }

            Assert.assertThat(sum, CoreMatchers.equalTo(30750));
            Person person = Person.builder().name("小明").age(18).build();
            Assert.assertThat(next, CoreMatchers.equalTo(person));
        } catch (IOException e) {

        } finally {
            // 删除临时文件
            FileUtils.deleteQuietly(tempFile);

        }

    }

    @Test
    public void toBeanIteratorWithQuote() {
        String file = this.getClass().getClassLoader().getResource("person-withQuote.csv").getFile();
        final File srcFile = FileUtils.getFile(file);

        final File tempFile = CsvToBeanUtil.toCanProcessCsvFile(srcFile, StandardCharsets.UTF_8, "@!@");

        // 打开临时文件流传入，在try-with-resource中完成业务处理，java在结束时，自己会进行流的关闭
        try (FileInputStream tempFileInputStream = FileUtils.openInputStream(tempFile)) {
            Iterator<Person> personIterator =
                CsvToBeanUtil.toBeanIterator(tempFileInputStream, StandardCharsets.UTF_8, Person.class, true);

            // 业务处理
            int sum = 0;
            Person next = null;
            while (personIterator.hasNext()) {
                sum++;
                next = personIterator.next();
            }

            Assert.assertThat(sum, CoreMatchers.equalTo(1000));
            LocalDate of = LocalDate.of(2019, 11, 17);
            Date from = Date.from(of.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            Person person = Person.builder().name("\\a").age(13).brithday(from).build();
            Assert.assertThat(next, CoreMatchers.equalTo(person));
        } catch (IOException e) {

        } finally {
            // 删除临时文件
            FileUtils.deleteQuietly(tempFile);

        }
    }

    @Test
    public void writeBeansToFile() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Person person = new Person();
            person.setAge(ThreadLocalRandom.current().nextInt(100));
            person.setName("\\a");
            person.setBrithday(new Date());

            personList.add(person);
        }

        CsvToBeanUtil.writeBeansToFile(personList, new File("ceshi"), StandardCharsets.UTF_8, "@!@");

        CsvToBeanUtil.writeBeansToFile(personList, new File("ceshi1"), StandardCharsets.UTF_8, "@!@", true);

    }

}