package com.mycompany.model;

/**
 * Created by X230 on 2016/10/15.
 */

public class BaseResponse {

    private String api_code;
    private String api_msg;

    public String getApi_code() {
        return api_code;
    }

    public void setApi_code(String api_code) {
        this.api_code = api_code;
    }

    public String getApi_msg() {
        return api_msg;
    }

    public void setApi_msg(String api_msg) {
        this.api_msg = api_msg;
    }
}
