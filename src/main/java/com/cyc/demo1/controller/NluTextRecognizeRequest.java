package com.cyc.demo1.controller;

import java.util.List;

/**
 * nlu文本识别请求对象
 * 
 * @author atchen
 */
public class NluTextRecognizeRequest {

    /**
     * 通话文本识别请求
     */
    private String operateType;

    /**
     * 上次识别机器人的问题，也就是A节点
     */
    private String nluQuestion;

    /**
     * ASR识别文本
     */
    private String asrText;

    /**
     * 当前节点组，如B1
     */
    private String currentNode;

    /**
     * 当前B节点和全局C节点所对应rule id的list
     */
    private List<String> ruleIdList;

    private String strategyType;

    public String getOperateType() {
        return operateType;
    }

    public String getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(String strategyType) {
        this.strategyType = strategyType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getNluQuestion() {
        return nluQuestion;
    }

    public void setNluQuestion(String nluQuestion) {
        this.nluQuestion = nluQuestion;
    }

    public String getAsrText() {
        return asrText;
    }

    public void setAsrText(String asrText) {
        this.asrText = asrText;
    }

    public List<String> getRuleIdList() {
        return ruleIdList;
    }

    public void setRuleIdList(List<String> ruleIdList) {
        this.ruleIdList = ruleIdList;
    }

    public String getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(String currentNode) {
        this.currentNode = currentNode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NluTextRecognizeRequest{");
        sb.append("operateType='").append(operateType).append('\'');
        sb.append(", nluQuestion='").append(nluQuestion).append('\'');
        sb.append(", asrText='").append(asrText).append('\'');
        sb.append(", currentNode='").append(currentNode).append('\'');
        sb.append(", ruleIdList=").append(ruleIdList);
        sb.append('}');
        return sb.toString();
    }
}
