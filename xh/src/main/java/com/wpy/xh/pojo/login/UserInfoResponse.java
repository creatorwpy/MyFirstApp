package com.wpy.xh.pojo.login;

import java.io.Serializable;
import java.util.ArrayList;

import library.mlibrary.util.common.CommonUtil;

/**
 * Created by harmy on 2016/8/10 0010.
 */
public class UserInfoResponse implements Serializable {
    private String action;
    private String code;
    private String blocklevel;
    private String isshower;
    private String iphone_reduce;
    private String xuanxiu_id;
    private String download_video_add;
    private String video_address_suffix;
    private String isyiren;
    private Info info;
    private YiRen yiren;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public YiRen getYiren() {
        return yiren;
    }

    public void setYiren(YiRen yiren) {
        this.yiren = yiren;
    }

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

    public String getBlocklevel() {
        return blocklevel;
    }

    public void setBlocklevel(String blocklevel) {
        this.blocklevel = blocklevel;
    }

    public String getIsshower() {
        return isshower;
    }

    public void setIsshower(String isshower) {
        this.isshower = isshower;
    }

    public String getIphone_reduce() {
        return iphone_reduce;
    }

    public void setIphone_reduce(String iphone_reduce) {
        this.iphone_reduce = iphone_reduce;
    }

    public String getXuanxiu_id() {
        return xuanxiu_id;
    }

    public void setXuanxiu_id(String xuanxiu_id) {
        this.xuanxiu_id = xuanxiu_id;
    }

    public String getDownload_video_add() {
        return download_video_add;
    }

    public void setDownload_video_add(String download_video_add) {
        this.download_video_add = download_video_add;
    }

    public String getVideo_address_suffix() {
        return video_address_suffix;
    }

    public void setVideo_address_suffix(String video_address_suffix) {
        this.video_address_suffix = video_address_suffix;
    }

    public String getIsyiren() {
        return isyiren;
    }

    public void setIsyiren(String isyiren) {
        this.isyiren = isyiren;
    }

    public static class Info implements Serializable {
        private String userid;
        private String totalcost;
        private String usernumber;
        private String nickname;
        private String balance;
        private String gender;
        private String viplevel;
        private String update_avatar_time;
        private String birthday;
        private String totalpoint;
        private String point;
        private String upload_cover;
        private String snsid;
        private String username;
        private String province;
        private String city;
        private String shengao;
        private String tizhong;
        private String dbzp;
        private String gxqm;
        private int myfav_num;
        private int favmy_num;
        private int haoyou_num;
        private ArrayList<String> sendmygift;
        private int todaypoint;
        private int JF_TIXIAN;
        private String JF_XNB;
        private String showcover;
        private String isfav;
        private String islahei;
        private int showtime;
        private int richlevel;
        private int starlevel;

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

        public String getTotalcost() {
            return totalcost;
        }

        public void setTotalcost(String totalcost) {
            this.totalcost = totalcost;
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

        public String getBalance() {
            if (CommonUtil.isEmpty(balance)) {
                return "0";
            }
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getViplevel() {
            return viplevel;
        }

        public void setViplevel(String viplevel) {
            this.viplevel = viplevel;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getTotalpoint() {
            return totalpoint;
        }

        public void setTotalpoint(String totalpoint) {
            this.totalpoint = totalpoint;
        }

        public String getPoint() {
            if (CommonUtil.isEmpty(point)) {
                return "0";
            }
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getUpload_cover() {
            return upload_cover;
        }

        public void setUpload_cover(String upload_cover) {
            this.upload_cover = upload_cover;
        }

        public String getSnsid() {
            return snsid;
        }

        public void setSnsid(String snsid) {
            this.snsid = snsid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
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

        public String getDbzp() {
            return dbzp;
        }

        public void setDbzp(String dbzp) {
            this.dbzp = dbzp;
        }

        public String getGxqm() {
            return gxqm;
        }

        public void setGxqm(String gxqm) {
            this.gxqm = gxqm;
        }

        public int getMyfav_num() {
            return myfav_num;
        }

        public void setMyfav_num(int myfav_num) {
            this.myfav_num = myfav_num;
        }

        public int getFavmy_num() {
            return favmy_num;
        }

        public void setFavmy_num(int favmy_num) {
            this.favmy_num = favmy_num;
        }

        public int getHaoyou_num() {
            return haoyou_num;
        }

        public void setHaoyou_num(int haoyou_num) {
            this.haoyou_num = haoyou_num;
        }

        public ArrayList<String> getSendmygift() {
            return sendmygift;
        }

        public void setSendmygift(ArrayList<String> sendmygift) {
            this.sendmygift = sendmygift;
        }

        public int getTodaypoint() {
            return todaypoint;
        }

        public void setTodaypoint(int todaypoint) {
            this.todaypoint = todaypoint;
        }

        public int getJF_TIXIAN() {
            return JF_TIXIAN;
        }

        public void setJF_TIXIAN(int JF_TIXIAN) {
            this.JF_TIXIAN = JF_TIXIAN;
        }

        public String getJF_XNB() {
            return JF_XNB;
        }

        public void setJF_XNB(String JF_XNB) {
            this.JF_XNB = JF_XNB;
        }

        public String getShowcover() {
            return showcover;
        }

        public void setShowcover(String showcover) {
            this.showcover = showcover;
        }

        public String getIsfav() {
            return isfav;
        }

        public void setIsfav(String isfav) {
            this.isfav = isfav;
        }

        public String getIslahei() {
            return islahei;
        }

        public void setIslahei(String islahei) {
            this.islahei = islahei;
        }

        public int getShowtime() {
            return showtime;
        }

        public void setShowtime(int showtime) {
            this.showtime = showtime;
        }

        public int getRichlevel() {
            return richlevel;
        }

        public void setRichlevel(int richlevel) {
            this.richlevel = richlevel;
        }

        public int getStarlevel() {
            return starlevel;
        }

        public void setStarlevel(int starlevel) {
            this.starlevel = starlevel;
        }
    }

    public static class YiRen implements Serializable {
        private String userid;
        private String shengao;
        private String tizhong;
        private String daibiaozuo;
        private String isshow;
        private String yirenpic;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
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

        public String getIsshow() {
            return isshow;
        }

        public void setIsshow(String isshow) {
            this.isshow = isshow;
        }

        public String getYirenpic() {
            return yirenpic;
        }

        public void setYirenpic(String yirenpic) {
            this.yirenpic = yirenpic;
        }
    }
}
