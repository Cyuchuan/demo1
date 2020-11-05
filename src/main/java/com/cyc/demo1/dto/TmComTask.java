package com.cyc.demo1.dto;

import java.io.Serializable;
import java.util.Date;

public class TmComTask implements Serializable {
    public static final String NAME_SPACE = "cn.webank.cnc.aobcase.db.dao.TmComTaskDao";

    public Long taskId;

    public String fileId;

    public Long itemId;

    public Long alertId;

    public Long caseId;

    public String appId;

    public String productCd;

    public String bizType;

    public String acctUid;

    public Date bizDate;

    public Date settingStartTime;

    public Date settingEndTime;

    public String carrierCode;

    public String deviceContactMethod;

    public String deviceType;

    public String callerPhoneNo;

    public String calledPhoneNo;

    public Date ringCallStartTime;

    public Date ringCallEndTime;

    public Integer ringDuration;

    public Date callStartTime;

    public Date callEndTime;

    public Integer callDuration;

    public String resultCode;

    public String resultDescription;

    public String clientCode;

    public String clientCodeDesc;

    public String auditTrail;

    public String resultBizNo;

    public String resultConsumerNo;

    public String status;

    public Integer version;

    public String userData;

    public Integer callPriority;

    public String callStrategy;

    public Integer callSeqNo;

    public Date createdDatetime;

    public Date lastModifiedDatetime;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getProductCd() {
        return productCd;
    }

    public void setProductCd(String productCd) {
        this.productCd = productCd == null ? null : productCd.trim();
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public String getAcctUid() {
        return acctUid;
    }

    public void setAcctUid(String acctUid) {
        this.acctUid = acctUid == null ? null : acctUid.trim();
    }

    public Date getBizDate() {
        return bizDate;
    }

    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
    }

    public Date getSettingStartTime() {
        return settingStartTime;
    }

    public void setSettingStartTime(Date settingStartTime) {
        this.settingStartTime = settingStartTime;
    }

    public Date getSettingEndTime() {
        return settingEndTime;
    }

    public void setSettingEndTime(Date settingEndTime) {
        this.settingEndTime = settingEndTime;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode == null ? null : carrierCode.trim();
    }

    public String getDeviceContactMethod() {
        return deviceContactMethod;
    }

    public void setDeviceContactMethod(String deviceContactMethod) {
        this.deviceContactMethod = deviceContactMethod == null ? null : deviceContactMethod.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getCallerPhoneNo() {
        return callerPhoneNo;
    }

    public void setCallerPhoneNo(String callerPhoneNo) {
        this.callerPhoneNo = callerPhoneNo == null ? null : callerPhoneNo.trim();
    }

    public String getCalledPhoneNo() {
        return calledPhoneNo;
    }

    public void setCalledPhoneNo(String calledPhoneNo) {
        this.calledPhoneNo = calledPhoneNo == null ? null : calledPhoneNo.trim();
    }

    public Date getRingCallStartTime() {
        return ringCallStartTime;
    }

    public void setRingCallStartTime(Date ringCallStartTime) {
        this.ringCallStartTime = ringCallStartTime;
    }

    public Date getRingCallEndTime() {
        return ringCallEndTime;
    }

    public void setRingCallEndTime(Date ringCallEndTime) {
        this.ringCallEndTime = ringCallEndTime;
    }

    public Integer getRingDuration() {
        return ringDuration;
    }

    public void setRingDuration(Integer ringDuration) {
        this.ringDuration = ringDuration;
    }

    public Date getCallStartTime() {
        return callStartTime;
    }

    public void setCallStartTime(Date callStartTime) {
        this.callStartTime = callStartTime;
    }

    public Date getCallEndTime() {
        return callEndTime;
    }

    public void setCallEndTime(Date callEndTime) {
        this.callEndTime = callEndTime;
    }

    public Integer getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(Integer callDuration) {
        this.callDuration = callDuration;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode == null ? null : resultCode.trim();
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription == null ? null : resultDescription.trim();
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode == null ? null : clientCode.trim();
    }

    public String getClientCodeDesc() {
        return clientCodeDesc;
    }

    public void setClientCodeDesc(String clientCodeDesc) {
        this.clientCodeDesc = clientCodeDesc == null ? null : clientCodeDesc.trim();
    }

    public String getAuditTrail() {
        return auditTrail;
    }

    public void setAuditTrail(String auditTrail) {
        this.auditTrail = auditTrail == null ? null : auditTrail.trim();
    }

    public String getResultBizNo() {
        return resultBizNo;
    }

    public void setResultBizNo(String resultBizNo) {
        this.resultBizNo = resultBizNo == null ? null : resultBizNo.trim();
    }

    public String getResultConsumerNo() {
        return resultConsumerNo;
    }

    public void setResultConsumerNo(String resultConsumerNo) {
        this.resultConsumerNo = resultConsumerNo == null ? null : resultConsumerNo.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData == null ? null : userData.trim();
    }

    public Integer getCallPriority() {
        return callPriority;
    }

    public void setCallPriority(Integer callPriority) {
        this.callPriority = callPriority;
    }

    public String getCallStrategy() {
        return callStrategy;
    }

    public void setCallStrategy(String callStrategy) {
        this.callStrategy = callStrategy == null ? null : callStrategy.trim();
    }

    public Integer getCallSeqNo() {
        return callSeqNo;
    }

    public void setCallSeqNo(Integer callSeqNo) {
        this.callSeqNo = callSeqNo;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Date getLastModifiedDatetime() {
        return lastModifiedDatetime;
    }

    public void setLastModifiedDatetime(Date lastModifiedDatetime) {
        this.lastModifiedDatetime = lastModifiedDatetime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskId=").append(taskId);
        sb.append(", NAME_SPACE=").append(NAME_SPACE);
        sb.append(", taskId=").append(taskId);
        sb.append(", fileId=").append(fileId);
        sb.append(", itemId=").append(itemId);
        sb.append(", alertId=").append(alertId);
        sb.append(", caseId=").append(caseId);
        sb.append(", appId=").append(appId);
        sb.append(", productCd=").append(productCd);
        sb.append(", bizType=").append(bizType);
        sb.append(", acctUid=").append(acctUid);
        sb.append(", bizDate=").append(bizDate);
        sb.append(", settingStartTime=").append(settingStartTime);
        sb.append(", settingEndTime=").append(settingEndTime);
        sb.append(", carrierCode=").append(carrierCode);
        sb.append(", deviceContactMethod=").append(deviceContactMethod);
        sb.append(", deviceType=").append(deviceType);
        sb.append(", callerPhoneNo=").append(callerPhoneNo);
        sb.append(", calledPhoneNo=").append(calledPhoneNo);
        sb.append(", ringCallStartTime=").append(ringCallStartTime);
        sb.append(", ringCallEndTime=").append(ringCallEndTime);
        sb.append(", ringDuration=").append(ringDuration);
        sb.append(", callStartTime=").append(callStartTime);
        sb.append(", callEndTime=").append(callEndTime);
        sb.append(", callDuration=").append(callDuration);
        sb.append(", resultCode=").append(resultCode);
        sb.append(", resultDescription=").append(resultDescription);
        sb.append(", clientCode=").append(clientCode);
        sb.append(", clientCodeDesc=").append(clientCodeDesc);
        sb.append(", auditTrail=").append(auditTrail);
        sb.append(", resultBizNo=").append(resultBizNo);
        sb.append(", resultConsumerNo=").append(resultConsumerNo);
        sb.append(", status=").append(status);
        sb.append(", version=").append(version);
        sb.append(", userData=").append(userData);
        sb.append(", callPriority=").append(callPriority);
        sb.append(", callStrategy=").append(callStrategy);
        sb.append(", callSeqNo=").append(callSeqNo);
        sb.append(", createdDatetime=").append(createdDatetime);
        sb.append(", lastModifiedDatetime=").append(lastModifiedDatetime);
        sb.append("]");
        return sb.toString();
    }

}