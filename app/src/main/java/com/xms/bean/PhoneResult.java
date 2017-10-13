package com.xms.bean;

/**
 * Created by dell on 2017/7/4.
 */

public class PhoneResult {

    /**
     * curDateTime : 1499134367205
     * info : 操作完成
     * recode : 10001000
     * result : 21001001
     * sessionId : 1F5B4FBFEBA62114DDE84ED1C3CFA66B
     * status : y
     * success : true
     */

    private long curDateTime;
    private String info;
    private int recode;
    private int result;
    private String sessionId;
    private String status;
    private boolean success;

    public long getCurDateTime() {
        return curDateTime;
    }

    public void setCurDateTime(long curDateTime) {
        this.curDateTime = curDateTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "PhoneResult{" +
                "curDateTime=" + curDateTime +
                ", info='" + info + '\'' +
                ", recode=" + recode +
                ", result=" + result +
                ", sessionId='" + sessionId + '\'' +
                ", status='" + status + '\'' +
                ", success=" + success +
                '}';
    }

    public int getRecode() {
        return recode;
    }

    public void setRecode(int recode) {
        this.recode = recode;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}