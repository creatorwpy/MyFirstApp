package com.wpy.xh;

import com.wpy.xh.pojo.ResponseResult;

/**
 * Created by harmy on 2016/9/2 0002.
 */
public class AppUpdate extends ResponseResult {
    private String version_must;
    private String version;
    private String url;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion_must() {
        return version_must;
    }

    public void setVersion_must(String version_must) {
        this.version_must = version_must;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
