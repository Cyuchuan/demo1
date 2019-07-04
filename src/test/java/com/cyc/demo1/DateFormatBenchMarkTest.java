package com.cyc.demo1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.cyc.demo1.dto.Pojo;
import com.cyc.demo1.validation.ValidationUtil;

/**
 * @author chenyuchuan
 */
@State(Scope.Benchmark)
public class DateFormatBenchMarkTest {
    static ValidationUtil bean;

    static List<Pojo> pojos;

    @BeforeClass
    public static void optionsBuild() throws RunnerException {
        Options build = new OptionsBuilder().include(".+").forks(1).build();

        new Runner(build).run();
    }

    @TearDown
    public void tearDown() {

    }

    @Setup
    public void setUp() {
        ConfigurableApplicationContext run = SpringApplication.run(Demo1Application.class);

        ArrayList<Pojo> objects = new ArrayList<>();

        Pojo pojo = new Pojo();
        pojo.setId("1111");
        pojo.setFileName("1321");
        pojo.setFileType("1");
        pojo.setFileName("20180205");
        pojo.setDate("20181231");

        objects.add(pojo);

        pojos = objects;

        bean = run.getBean(ValidationUtil.class);
    }

    @Test
    @Threads(1)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 5, time = 1)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Benchmark
    public void dateFormatBenchMarkTest() {
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

        yyyyMMdd.format(new Date());

    }

    // @Test
    // @Threads(1)
    // @Warmup(iterations = 5, time = 1)
    // @Measurement(iterations = 5, time = 1)
    // @BenchmarkMode(Mode.Throughput)
    // @OutputTimeUnit(TimeUnit.SECONDS)
    // @Benchmark
    public void beanUtil() {
        bean.validationField(pojos);
    }
}
