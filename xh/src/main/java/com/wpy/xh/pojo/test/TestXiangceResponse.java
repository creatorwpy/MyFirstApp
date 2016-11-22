package com.wpy.xh.pojo.test;

import com.wpy.xh.pojo.ResponseResult;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by X230 on 2016/11/9.
 */

public class TestXiangceResponse extends ResponseResult {
    private ArrayList<Photo> photo;
    private Yireninfo yireninfo;

    public ArrayList<Photo> getPhoto() {
        return photo;
    }

    public void setPhoto(ArrayList<Photo> photo) {
        this.photo = photo;
    }

    public Yireninfo getYireninfo() {
        return yireninfo;
    }

    public void setYireninfo(Yireninfo yireninfo) {
        this.yireninfo = yireninfo;
    }

    public static class Yireninfo implements Serializable {
        private String userid;
        private String nickname;
        private String usernumber;
        private String city;
        private String birthday;
        private String gender;
        private String xingzuo;
        private String shengao;
        private String tizhong;
        private String daibiaozuo;
        private String gxqm;
        private String update_avatar_time;
        private String piaoshu;
        private String piaoshu2;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUsernumber() {
            return usernumber;
        }

        public void setUsernumber(String usernumber) {
            this.usernumber = usernumber;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getXingzuo() {
            return xingzuo;
        }

        public void setXingzuo(String xingzuo) {
            this.xingzuo = xingzuo;
        }

        public String getShengao() {
            return shengao;
        }

        public void setShengao(String shengao) {
            this.shengao = shengao;
        }

        public String getTizhong() {
            return tizhong;
        }

        public void setTizhong(String tizhong) {
            this.tizhong = tizhong;
        }

        public String getDaibiaozuo() {
            return daibiaozuo;
        }

        public void setDaibiaozuo(String daibiaozuo) {
            this.daibiaozuo = daibiaozuo;
        }

        public String getGxqm() {
            return gxqm;
        }

        public void setGxqm(String gxqm) {
            this.gxqm = gxqm;
        }

        public String getUpdate_avatar_time() {
            return update_avatar_time;
        }

        public void setUpdate_avatar_time(String update_avatar_time) {
            this.update_avatar_time = update_avatar_time;
        }

        public String getPiaoshu() {
            return piaoshu;
        }

        public void setPiaoshu(String piaoshu) {
            this.piaoshu = piaoshu;
        }

        public String getPiaoshu2() {
            return piaoshu2;
        }

        public void setPiaoshu2(String piaoshu2) {
            this.piaoshu2 = piaoshu2;
        }
    }

    public static class Photo implements Serializable {
        private String photoid;
        private String userid;
        private String title;
        private String path;
        private String clickcount;
        private String zancount;
        private String zan_userid;
        private String commentcount;
        private String addtime;
        private String current;
        private String isvideo;
        private String videoadd;
        private String orderby;
        private String status;
        private String orderby_row;
        private String text;
        private String imglink;
        private String like_text;

        public void setPhotoid(String photoid) {
            this.photoid = photoid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public void setClickcount(String clickcount) {
            this.clickcount = clickcount;
        }

        public void setZancount(String zancount) {
            this.zancount = zancount;
        }

        public void setZan_userid(String zan_userid) {
            this.zan_userid = zan_userid;
        }

        public void setCommentcount(String commentcount) {
            this.commentcount = commentcount;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public void setCurrent(String current) {
            this.current = current;
        }

        public void setIsvideo(String isvideo) {
            this.isvideo = isvideo;
        }

        public void setVideoadd(String videoadd) {
            this.videoadd = videoadd;
        }

        public void setOrderby(String orderby) {
            this.orderby = orderby;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setOrderby_row(String orderby_row) {
            this.orderby_row = orderby_row;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setImglink(String imglink) {
            this.imglink = imglink;
        }

        public String getPhotoid() {
            return photoid;
        }

        public String getUserid() {
            return userid;
        }

        public String getTitle() {
            return title;
        }

        public String getPath() {
            return path;
        }

        public String getClickcount() {
            return clickcount;
        }

        public String getZancount() {
            return zancount;
        }

        public String getZan_userid() {
            return zan_userid;
        }

        public String getCommentcount() {
            return commentcount;
        }

        public String getAddtime() {
            return addtime;
        }

        public String getCurrent() {
            return current;
        }

        public String getIsvideo() {
            return isvideo;
        }

        public String getVideoadd() {
            return videoadd;
        }

        public String getOrderby() {
            return orderby;
        }

        public String getStatus() {
            return status;
        }

        public String getOrderby_row() {
            return orderby_row;
        }

        public String getText() {
            return text;
        }

        public String getImglink() {
            return imglink;
        }

        public String getLike_text() {
            return like_text;
        }

        public void setLike_text(String like_text) {
            this.like_text = like_text;
        }
    }
}
