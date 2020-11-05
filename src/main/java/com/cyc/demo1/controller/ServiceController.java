package com.cyc.demo1.controller;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyc.demo1.dto.Result;
import com.cyc.demo1.entity.User;
import com.cyc.demo1.entity.User3;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@RestController
@RequestMapping("service")
@Slf4j
public class ServiceController {
    @Autowired
    private Validator validator;

    @RequestMapping("print")
    public Result printService(User user, BindingResult bindingResult) {
        log.warn("{}", user);
        if (bindingResult.hasFieldErrors()) {
            StringBuilder stringBuilder = new StringBuilder(32);
            bindingResult.getFieldErrors().forEach(fieldError -> stringBuilder.append(fieldError.getField()).append(":")
                .append(fieldError.getDefaultMessage()).append("\n"));
            return Result.builder().code(400).message(stringBuilder.toString()).build();
        }

        // Set<ConstraintViolation<User>> validate = validator.validate(user);
        // for (ConstraintViolation<User> userConstraintViolation : validate) {
        // log.error("{}",userConstraintViolation.getMessage());
        // }

        return Result.builder().code(200).data(user).build();
    }

    @RequestMapping("user3")
    public Result userService(@Validated User3 user, BindingResult bindingResult) {
        log.warn("{}", user);
        if (bindingResult.hasFieldErrors()) {
            StringBuilder stringBuilder = new StringBuilder(32);
            bindingResult.getFieldErrors().forEach(fieldError -> stringBuilder.append(fieldError.getField()).append(":")
                .append(fieldError.getDefaultMessage()).append("\n"));
            return Result.builder().code(400).message(stringBuilder.toString()).build();
        }

        return Result.builder().code(200).data(user).build();
    }

    @RequestMapping("test")
    public String test() {
        String s = new String("test");
        return s;
    }

    @RequestMapping("test1")
    public String test1() {
        String hhhh = new String("hhhh");
        return hhhh;
    }

    @RequestMapping("test2")
    public String test2() {
        String s = new String("test1----123321");
        return s;
    }

    @RequestMapping("runtime")
    public String runtimeExceptionTest() {
        throw new RuntimeException("出现了运行时异常");
    }

    @RequestMapping("nlu")
    public NluTextRecognizeResponse nlu(@RequestBody NluTextRecognizeRequest nluTextRecognizeRequest) {
        log.error("{}", nluTextRecognizeRequest);
        NluTextRecognizeResponse nluTextRecognizeResponse = new NluTextRecognizeResponse();
        nluTextRecognizeResponse.setCode(0);
        nluTextRecognizeResponse.setMsg("312");
        nluTextRecognizeResponse.setAsrCorrect("312");
        nluTextRecognizeResponse.setNluRecognitionType("321");
        nluTextRecognizeResponse.setLongSentenceFix("321");
        nluTextRecognizeResponse.setNluRuleIdList(Lists.newArrayList("3213"));

        return nluTextRecognizeResponse;
    }
}
