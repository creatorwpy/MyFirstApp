package com.wpy.xh.pojo.userlist;

import java.io.Serializable;

/**
 * Created by harmy on 2016/8/24 0024.
 */
public class FollowItem implements Serializable{
    private String userid;
    private String usernumber;
    private String update_avatar_time;
    private String nickname;
    private String gxqm;
    private String isshowing;
    private String viewernum;
    private String showcover;

    public String getShowcover() {
        return showcover;
    }

    public void setShowcover(String showcover) {
        this.showcover = showcover;
    }

    public String getViewernum() {
        return viewernum;
    }

    public void setViewernum(String viewernum) {
        this.viewernum = viewernum;
    }

    public String getUpdate_avatar_time() {
        return update_avatar_time;
    }

    public void setUpdate_avatar_time(String update_avatar_time) {
        this.update_avatar_time = update_avatar_time;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGxqm() {
        return gxqm;
    }

    public void setGxqm(String gxqm) {
        this.gxqm = gxqm;
    }

    public String getIsshowing() {
        return isshowing;
    }

    public void setIsshowing(String isshowing) {
        this.isshowing = isshowing;
    }
}
