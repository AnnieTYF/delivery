package com.tyf.community.entity;
/**
 * @author ATK
 */
public class Result {
    private String msg;
    private Integer code;
    private Object data;

    public Result() {
    }

    public Result(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }


    public Result(String msg, Integer code, Object data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
