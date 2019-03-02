package cn.howtoplay.attendance.common;

import java.io.Serializable;

/**
 * @author xiaoqi on 2019/2/25
 */
public class Payload<T> implements Serializable {

    private T payload;
    private String code;
    private String msg;

    public Payload() {
    }

    public Payload(T payload) {
        this.payload = payload;
        this.code = "200";
        this.msg = "success";
    }

    public Payload(T payload, String code, String msg) {
        this.payload = payload;
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
