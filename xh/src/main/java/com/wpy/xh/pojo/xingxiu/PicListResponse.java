package com.wpy.xh.pojo.xingxiu;

import com.wpy.xh.pojo.ResponseResult;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by harmy on 2016/8/18 0018.
 */
public class PicListResponse extends ResponseResult {
    private YiRen yireninfo;
    private ArrayList<PhotoItem> photo;

    public ArrayList<PhotoItem> getPhoto() {
        return photo;
    }

    public void setPhoto(ArrayList<PhotoItem> photo) {
        this.photo = photo;
    }

    public YiRen getYireninfo() {
        return yireninfo;
    }

    public void setYireninfo(YiRen yireninfo) {
        this.yireninfo = yireninfo;
    }

    public static class YiRen implements Serializable {
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
        private String yiming;
        private String update_avatar_time;
        private long piaoshu;
        private String gxqm;
        private String piaoshu2;

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

        public String getYiming() {
            return yiming;
        }

        public void setYiming(String yiming) {
            this.yiming = yiming;
        }

        public long getPiaoshu() {
            return piaoshu;
        }

        public void setPiaoshu(long piaoshu) {
            this.piaoshu = piaoshu;
        }

        public String getGxqm() {
            return gxqm;
        }

        public void setGxqm(String gxqm) {
            this.gxqm = gxqm;
        }

        public String getPiaoshu2() {
            return piaoshu2;
        }

        public void setPiaoshu2(String piaoshu2) {
            this.piaoshu2 = piaoshu2;
        }
    }

    public static class PhotoItem implements Serializable {
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
        private String iszan;

        public String getPhotoid() {
            return photoid;
        }

        public void setPhotoid(String photoid) {
            this.photoid = photoid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getClickcount() {
            return clickcount;
        }

        public void setClickcount(String clickcount) {
            this.clickcount = clickcount;
        }

        public String getZancount() {
            return zancount;
        }

        public void setZancount(String zancount) {
            this.zancount = zancount;
        }

        public String getZan_userid() {
            return zan_userid;
        }

        public void setZan_userid(String zan_userid) {
            this.zan_userid = zan_userid;
        }

        public String getCommentcount() {
            return commentcount;
        }

        public void setCommentcount(String commentcount) {
            this.commentcount = commentcount;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getCurrent() {
            return current;
        }

        public void setCurrent(String current) {
            this.current = current;
        }

        public String getIsvideo() {
            return isvideo;
        }

        public void setIsvideo(String isvideo) {
            this.isvideo = isvideo;
        }

        public String getVideoadd() {
            return videoadd;
        }

        public void setVideoadd(String videoadd) {
            this.videoadd = videoadd;
        }

        public String getOrderby() {
            return orderby;
        }

        public void setOrderby(String orderby) {
            this.orderby = orderby;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOrderby_row() {
            return orderby_row;
        }

        public void setOrderby_row(String orderby_row) {
            this.orderby_row = orderby_row;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getImglink() {
            return imglink;
        }

        public void setImglink(String imglink) {
            this.imglink = imglink;
        }

        public String getLike_text() {
            return like_text;
        }

        public void setLike_text(String like_text) {
            this.like_text = like_text;
        }

        public String getIszan() {
            return iszan;
        }

        public void setIszan(String iszan) {
            this.iszan = iszan;
        }
    }
}
