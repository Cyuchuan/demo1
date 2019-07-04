package com.cyc.demo1;

import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.cyc.demo1.random.RandomUtil;

/**
 * @author chenyuchuan
 */
public class SimpleDateAndJodaTimeTest {
    @BeforeClass
    public static void optionsBuild() throws RunnerException {
        Options build = new OptionsBuilder().include(SimpleDateAndJodaTimeTest.class.getSimpleName()).forks(1).build();

        new Runner(build).run();
    }

    // @Test
    // @Threads(5)
    // @Warmup(iterations = 5, time = 1)
    // @Measurement(iterations = 5, time = 1)
    // @BenchmarkMode(Mode.Throughput)
    // @OutputTimeUnit(TimeUnit.SECONDS)
    // @Benchmark
    // public void simpleDate() throws ParseException {
    // Calendar instance = Calendar.getInstance();
    // instance.setTime(new Date());
    //
    // Calendar instance1 = Calendar.getInstance();
    // instance.setTime(new Date());
    //
    // boolean b = instance.get(0) == instance1.get(0) && instance.get(1) == instance1.get(1)
    // && instance.get(6) == instance1.get(6);
    // }
    //
    // @Test
    // @Threads(5)
    // @Warmup(iterations = 4, time = 1)
    // @Measurement(iterations = 4, time = 1)
    // @BenchmarkMode(Mode.Throughput)
    // @OutputTimeUnit(TimeUnit.SECONDS)
    // @Benchmark
    // public void jodaTime() {
    // DateTime dateTime = new DateTime(new Date());
    // DateTime dateTime1 = new DateTime(new Date());
    //
    // boolean b = dateTime.getYear() == dateTime1.getYear();
    // boolean d = dateTime.getDayOfYear() == dateTime1.getDayOfYear();
    //
    // boolean b1 = b && d;
    // }

    @Test
    @Threads(5)
    @Warmup(iterations = 4, time = 1)
    @Measurement(iterations = 4, time = 1)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Benchmark
    public void threadLocal() {
        RandomUtil.nextInt(7, 14);
    }

    @Test
    @Threads(5)
    @Warmup(iterations = 4, time = 1)
    @Measurement(iterations = 4, time = 1)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Benchmark
    public void noThreadLocal() {
        RandomUtil.nextIntWithoutThreadLocal(7, 14);
    }

}
