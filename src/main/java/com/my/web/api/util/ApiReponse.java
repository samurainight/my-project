package com.my.web.api.util;

import java.util.HashMap;

public class ApiReponse {
    private int    code = 0;
    private String msg = "ok";
    private Object data = new HashMap<>();

    public ApiReponse(int code, String msg, Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ApiReponse(){}

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public void setError(int code){
        setError(code,"fail");
    }

    public void setError(int code, String errmsg){
        this.code = code;
        this.msg = errmsg;
    }
}
