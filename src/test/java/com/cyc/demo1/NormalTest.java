package com.cyc.demo1;

import java.io.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.io.ClassPathResource;

import com.cyc.demo1.dto.Pojo;
import com.cyc.demo1.entity.User3;
import com.cyc.demo1.eventservice.A;
import com.cyc.demo1.eventservice.B;
import com.cyc.demo1.exception.NoBatchProcessException;
import com.cyc.demo1.jdkproxy.MathHandle;
import com.cyc.demo1.jdkproxy.MathService;
import com.cyc.demo1.jdkproxy.MathServiceImp;
import com.cyc.demo1.listener.Service;
import com.cyc.demo1.pojo.Person;
import com.cyc.demo1.random.RandomUtil;
import com.cyc.demo1.util.CompressorUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Slf4j
public class NormalTest {
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();

    }

    @Test
    public void test() {

        Map<Integer, List<Integer>> map = new HashMap<>();
        map.put(new Integer(1), new LinkedList<>());
        Pojo pojo = new Pojo();
        BeanWrapper bw = new BeanWrapperImpl(pojo);
        bw.setPropertyValue("mapOfLists[1][0]", new Integer(5));
    }

    @Test
    public void test12() throws Exception {
        URL resource = NormalTest.class.getClassLoader().getResource("test/nio-test-1.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.openStream()));

        String s = bufferedReader.readLine();
        log.error("{}", s);
    }

    @Test
    public void threadPoolExecutorTest() {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        int i = threadGroup.activeCount();
        Thread[] threads = new Thread[i];
        threadGroup.enumerate(threads);

        // ThreadPoolExecutor threadPoolExecutor =
        // new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));
        //
        // threadPoolExecutor.execute(new Runnable() {
        // @Override
        // public void run() {
        // log.error("debug 线程池 ThreadPoolExecutor");
        // }
        // });

    }

    @Test
    public void stringDate() throws ParseException {
        String a = "14:00:00";
        Date date = DateUtils.parseDate(a, "HH:mm:ss");

        String format2 = DateFormatUtils.format(new Date(), "HH:mm:ss");
        log.error("{}", format2.compareTo(a));

        Date date1 = DateUtils.parseDate(format2, "HH:mm:ss");
        log.error("{}", date1);

        log.error("{}", date1.after(date));

    }

    @Test
    public void test123() throws Exception {

        Person person = new Person();
        person.setBrithday(new Date());
        person.setAge(0);
        person.setName("");
        HashSet<Date> objects = new HashSet<>();
        objects.add(new Date(10000000));
        objects.add(new Date(20000000));
        objects.add(new Date(30000000));
        objects.add(new Date(40000000));
        objects.add(new Date(50000000));

        person.setDateSet(objects);

        HashSet<Person.DialTimeZone> dialTimeZoneSet = new HashSet<>();

        Person.DialTimeZone dialTimeZone = new Person.DialTimeZone();
        dialTimeZone.setStartTime(new Date());
        dialTimeZone.setEndTime(new Date(new Date().getTime() + 500000L));
        dialTimeZoneSet.add(dialTimeZone);
        person.setDialTimeZoneSet(dialTimeZoneSet);

        String string = MAPPER.writeValueAsString(person);
        log.error("{}", string);

        Person person1 = MAPPER.readValue(string, Person.class);
        log.error("{}", person1);
    }

    @Test
    public void threadLocalTest() {
        InheritableThreadLocal<String> objectInheritableThreadLocal = new InheritableThreadLocal<>();
        objectInheritableThreadLocal.set("父线程的值");

        new Thread(new Runnable() {
            @Override
            public void run() {
                objectInheritableThreadLocal.set("A");
                String s = objectInheritableThreadLocal.get();
                log.error("{}", s);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                objectInheritableThreadLocal.set("B");

                String s = objectInheritableThreadLocal.get();
                log.error("{}", s);
            }
        }).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.error("{}", objectInheritableThreadLocal.get());
    }

    @Test
    public void cyclicBarrierTest() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(30, 30, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    log.error("线程:" + Thread.currentThread() + " 进入等待");
                    cyclicBarrier.await();
                    log.error("线程:" + Thread.currentThread() + " 结束等待");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        };
        while (true) {
            threadPoolExecutor.execute(runnable);
            threadPoolExecutor.execute(runnable);
            threadPoolExecutor.execute(runnable);
            threadPoolExecutor.execute(runnable);
            threadPoolExecutor.execute(runnable);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test1() {

        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());

        Calendar instance1 = Calendar.getInstance();
        instance.setTime(new Date());

        boolean b = instance.get(0) == instance1.get(0) && instance.get(1) == instance1.get(1)
            && instance.get(6) == instance1.get(6);

    }

    @Test
    public void testTimeout() {
        DateTime dateTime = new DateTime(System.currentTimeMillis());
        boolean beforeNow = dateTime.isBeforeNow();
        log.error("{}", beforeNow);
    }

    @Test
    public void methodHandleTest() throws Throwable {
        Pojo pojo = new Pojo();
        pojo.setNotValidation("这是一次验证");
        MethodHandles.Lookup lookup = Pojo.getLookup();
        MethodType.methodType(String.class);
        MethodHandle notValidation = lookup.findGetter(Pojo.class, "notValidation", String.class);
        Object invoke = notValidation.invoke(pojo);
        log.error("{}", invoke);
    }

    @Test
    public void methodServiceTest() throws Exception {
        A a = new A();
        B b = new B();
        Method service = Service.class.getMethod("service");
        service.invoke(a);
        service.invoke(b);

    }

    @Test
    public void methodHandleServiceTest() throws Throwable {
        A a = new A();
        B b = new B();
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(void.class);

        MethodHandle aservice1 = lookup.findVirtual(A.class, "service", methodType);
        MethodHandle bservice1 = lookup.findVirtual(B.class, "service", methodType);
        Object invoke = aservice1.invoke(a);
        bservice1.invoke(b);
    }

    @Test
    public void exceptionTest() {
        new NoBatchProcessException();
    }

    @Test
    public void stringTokenTest() {
        StringTokenizer stringTokenizer = new StringTokenizer("dasda,dsadasd;fdsiufhsi,;dasdsada", ",;");
        while (stringTokenizer.hasMoreTokens()) {
            log.error("{}", stringTokenizer.nextToken());
        }

        String[] string = new String("asdfgh").split(".*+");
        log.error("\n");
        for (String s : string) {
            log.error("{}", s);
        }
    }

    @Test
    public void test3() {

        for (int i = 0; i < 30; i++) {
            long l = RandomUtil.nextLongBetween(-100, 10);
            log.error("nextLongBetween{}", l);

        }

        for (int i = 0; i < 30; i++) {
            long l = RandomUtil.nextLong(14);
            log.error("nextLong(bound){}", l);

        }

        for (int i = 0; i < 30; i++) {
            long l = RandomUtil.nextLong();
            log.error("nextLong{}", l);

        }

    }

    @Test
    public void validationTest() {
        log.error("开始");
        User3 user = new User3();
        user.setName("123");
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<User3>> validate = validator.validate(user);

        for (ConstraintViolation<User3> user3ConstraintViolation : validate) {
            log.error("{}", user3ConstraintViolation.getMessage());
        }
    }

    @Test
    public void methodHandlesTest() {

    }

    @Test
    public void splitTest() {

        String s = "a,b,c,,";
        String[] split = s.split(",");
        log.error("{}  {}", split.length, split);
    }

    @Test
    public void encodeTest() throws Exception {
        InputStream inputStream = this.getClass().getClassLoader().getResource("test.txt").openStream();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("GBK")));

        log.error("{}", bufferedReader.readLine());

        bufferedReader.close();
    }

    @Test
    public void append() throws IOException {
        String baseUrl = "http://123456.2123156.46";
        log.error("{}", baseUrl + '/');

        log.error("{}", UUID.randomUUID().toString().replaceAll("-", ""));
        log.error("{}", UUID.randomUUID().toString());
        int i = baseUrl.lastIndexOf(".");
        log.error("{}", baseUrl.substring(i));

        ClassPathResource classPathResource = new ClassPathResource("classpath:template/xxxxx");

        InputStream inputStream = classPathResource.getInputStream();
    }

    @Test
    public void unTarTest() throws IOException, URISyntaxException {

        Path test = Paths.get("C:\\Users\\40493\\Desktop\\github\\demo1\\target\\test-classes\\test");
        log.error("{}", test.toAbsolutePath());

        DirectoryStream<Path> paths = Files.newDirectoryStream(test);

        for (Path path : paths) {
            log.error("是否是目录:{}", Files.isDirectory(path));

            log.error("{}", path.getFileName());
            log.error("文件内容为：{}", Files.newBufferedReader(path).readLine());

        }

    }

    @Test
    public void testFile() throws Exception {
        String inputFile = this.getClass().getClassLoader().getResource("test.txt").getPath();
        BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(inputFile.substring(1)));

        Path dir = Paths.get(".", UUID.randomUUID().toString());
        if (!Files.exists(dir)) {
            Files.createDirectory(dir);
        }

        Path tempFile = dir.resolve(UUID.randomUUID().toString());
        if (!Files.exists(tempFile)) {
            Files.createFile(tempFile);

        }

        BufferedWriter bufferedWriter = Files.newBufferedWriter(tempFile);
        log.error("{}", bufferedWriter);
        String temp;
        while ((temp = bufferedReader.readLine()) != null) {
            if ("".equals(temp)) {
                continue;
            }

            log.error("{}", temp);
            bufferedWriter.append(temp);
            bufferedWriter.newLine();
        }

        bufferedWriter.flush();
        bufferedReader.close();
        bufferedWriter.close();

        Thread.sleep(1000);
        Files.delete(tempFile);
        Thread.sleep(2000);

        Files.deleteIfExists(dir);

    }

    @Test
    public void classPathIOTest() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("test.txt");
        InputStream inputStream = classPathResource.getInputStream();
        BufferedReader buffer = IOUtils.buffer(new InputStreamReader(inputStream));

        String temp;

        while ((temp = buffer.readLine()) != null) {
            log.error("{}", temp);
        }
    }

    @Test
    public void compressorTest() throws Exception {
        // List<File> test = CompressorUtil.unCompressTarGZ(new File("G120150909.tar.gz"), new File("."));

        // List<File> test = CompressorUtil.unCompress(new File("000000_0(1).gz"), new File("test"));

        ArrayList<File> files = new ArrayList<>();
        files.add(new File("1.txt"));
        files.add(new File("2.txt"));
        files.add(new File("3.txt"));
        files.add(new File("4.txt"));

        File file = CompressorUtil.packageToTarGzFile(files, new File("test1.tar.gz"));
        List<File> unCompressTarGZ = CompressorUtil.unCompressTarGZ(file, new File("unCompressTarGZ"));
        log.error("{}", file);
        log.error("{}", unCompressTarGZ);

    }

    @Test
    public void fileTest() {
        File file = new File("123", "123123");

        file.mkdirs();
        log.error("{}", file.getPath());
        log.error("{}", file.getName());
        log.error("{}", file.getAbsolutePath());
        log.error("{}", file.getAbsoluteFile());

    }

    @Test
    public void getComponentTest() {
        List<String> list = new ArrayList<>();

        list.add("小明");
        list.add("小红");

        String[] s = {"小明", "小红"};
        List<String> strings = Arrays.asList(s);

        ParameterizedType genericSuperclass = (ParameterizedType)(list.getClass().getGenericSuperclass());
        Class<? extends List> aClass = strings.getClass();
        Class<?> componentType = aClass.getComponentType();
        log.error("{}", aClass);
        log.error("{}", componentType);
        log.error("{}", s.getClass().getComponentType());

    }

    @Test
    public void jdkProxy() {
        MathServiceImp mathServiceImp = new MathServiceImp();
        MathHandle mathHandle = new MathHandle(mathServiceImp);

        MathService mathService = (MathService)Proxy.newProxyInstance(mathHandle.getClass().getClassLoader(),
            mathServiceImp.getClass().getInterfaces(), mathHandle);

        mathService.add(1, 2);
        mathService.subtraction(2, 1);
        mathService.multiplication(2, 8);
        mathService.division(10, 2);

    }
}
