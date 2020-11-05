package com.cyc.demo1.controller;

import java.util.List;

import lombok.Data;

/**
 * nlu意图识别响应
 * 
 * @author atchen
 */
@Data
public class NluTextRecognizeResponse {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 描述
     */
    private String msg;

    /**
     * asr纠错
     */
    private String asrCorrect;

    /**
     * 识别类型细节
     */
    private String nluRecognitionType;

    /**
     * 长句改写
     */
    private String longSentenceFix;

    /**
     * 规则节点
     */
    private List<String> nluRuleIdList;

}
