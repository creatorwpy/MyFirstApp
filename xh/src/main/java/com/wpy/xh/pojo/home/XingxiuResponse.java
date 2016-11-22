package com.wpy.xh.pojo.home;

import com.wpy.xh.pojo.ResponseResult;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by harmy on 2016/8/9 0009.
 */
public class XingxiuResponse extends ResponseResult {
    private ArrayList<XingxiuItem> response;

    public ArrayList<XingxiuItem> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<XingxiuItem> response) {
        this.response = response;
    }

    public static class XingxiuItem implements Serializable {
        private String xuanxiu_id;
        private String title;
        private ArrayList<ItemInfo> data;

        public String getXuanxiu_id() {
            return xuanxiu_id;
        }

        public void setXuanxiu_id(String xuanxiu_id) {
            this.xuanxiu_id = xuanxiu_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ArrayList<ItemInfo> getData() {
            return data;
        }

        public void setData(ArrayList<ItemInfo> data) {
            this.data = data;
        }
    }

    public static class ItemInfo implements Serializable {
        private String userid;
        private String nickname;
        private String usernumber;
        private String xuanxiu_id;
        private String piaoshu;
        private String yirenpic;
        private String current;
        private String yiming;
        private String piaoshu2;

        public String getXuanxiu_id() {
            return xuanxiu_id;
        }

        public void setXuanxiu_id(String xuanxiu_id) {
            this.xuanxiu_id = xuanxiu_id;
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

        public String getPiaoshu() {
            return piaoshu;
        }

        public void setPiaoshu(String piaoshu) {
            this.piaoshu = piaoshu;
        }

        public String getYirenpic() {
            return yirenpic;
        }

        public void setYirenpic(String yirenpic) {
            this.yirenpic = yirenpic;
        }

        public String getCurrent() {
            return current;
        }

        public void setCurrent(String current) {
            this.current = current;
        }

        public String getYiming() {
            return yiming;
        }

        public void setYiming(String yiming) {
            this.yiming = yiming;
        }

        public String getPiaoshu2() {
            return piaoshu2;
        }

        public void setPiaoshu2(String piaoshu2) {
            this.piaoshu2 = piaoshu2;
        }
    }
}
