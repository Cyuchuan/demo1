package com.cyc.demo1.dao;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestDateMapperTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void insert() throws ParseException {
        Date begin = DateUtils.parseDate("2020-06-01 01:00:00", "yyyy-MM-dd HH:mm:sss");

        Long id = null;
        Date end = DateUtils.parseDate("2020-06-01 23:00:00", "yyyy-MM-dd HH:mm:sss");

        for (long i = 500000L; i < 1000000; i++) {
            jdbcTemplate.update("INSERT INTO TM_COM_TASK\n"
                + "(TASK_ID, FILE_ID, ITEM_ID, ALERT_ID, CASE_ID, APP_ID, PRODUCT_CD, BIZ_TYPE, ACCT_UID, BIZ_DATE, SETTING_START_TIME, SETTING_END_TIME, CARRIER_CODE, DEVICE_CONTACT_METHOD, DEVICE_TYPE, CALLER_PHONE_NO, CALLED_PHONE_NO, RING_CALL_START_TIME, RING_CALL_END_TIME, RING_DURATION, CALL_START_TIME, CALL_END_TIME, CALL_DURATION, RESULT_CODE, RESULT_DESCRIPTION, CLIENT_CODE, CLIENT_CODE_DESC, AUDIT_TRAIL, RESULT_BIZ_NO, RESULT_CONSUMER_NO, STATUS, VERSION, USER_DATA, CALL_PRIORITY, CALL_STRATEGY, CALL_SEQ_NO, CREATED_DATETIME, LAST_MODIFIED_DATETIME)\n"
                + "VALUES(?, '86000003264359840227901750505867010000015922160022796322', 2980000000000000005, 2980000000000000005, 2980000000000000005, 'SYSTEM', 'SYSTEM', 'A', '136d9700e98e48708ca02395d0cea7a3', '2020-05-28', ?, ?, 'UNICOM_SH', 'CTI', 'M', '*', '18826136653', NULL, NULL, 0, NULL, '2020-06-15 18:16:10.0', 0, 'EXE', '前置其他异常', 'ERRO', '其他异常', NULL, '2006150FA06752000000000001198840', '2006151FA06752000000000001198840', 'DONE', 1, NULL, 50, '1', 1, '2020-06-15 18:13:23.0', '2020-06-15 18:16:10.0')",
                i, begin, end);

            if (i % 10000 == 0) {
                begin = DateUtils.addDays(begin, 1);
                end = DateUtils.addDays(end, 1);

            }
        }

    }

}