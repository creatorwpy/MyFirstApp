package com.wpy.xh.pojo;

import java.io.Serializable;

/**
 * Created by harmy on 2016/8/4 0004.
 */
public class ResponseResult implements Serializable {
    protected String action;
    protected String code;
    protected String info;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
