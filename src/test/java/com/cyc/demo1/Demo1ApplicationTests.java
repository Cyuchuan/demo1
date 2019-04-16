package com.cyc.demo1;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.cyc.demo1.entity.User3;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.annotation.Validated;

import com.cyc.demo1.dao.TestFieldMapper;
import com.cyc.demo1.entity.TestField;
import com.cyc.demo1.entity.TestFieldExample;
import com.cyc.demo1.entity.User;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Demo1ApplicationTests {
    @Autowired
    private TestFieldMapper testFieldMapper;

    @Autowired
    private Validator validator;

    @Test
    public void contextLoads() {
        TestFieldExample testFieldExample = new TestFieldExample();
        List<TestField> testFields = testFieldMapper.selectByExample(testFieldExample);
        log.error("{}", testFields);
    }

    @Test
    public void validationTest() {
        log.error("开始");
        User3 user = new User3();
        user.setName("123");
        Set<ConstraintViolation<User3>> asd = validator.validate(user);

        for (ConstraintViolation<User3> stringConstraintViolation : asd) {
            log.error("{}",stringConstraintViolation.getMessage());
        }
    }

}
