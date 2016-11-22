package com.wpy.xh.pojo.login;

import java.io.Serializable;

/**
 * Created by X230 on 2016/11/17.
 */

public class LoginResponse implements Serializable {

    private String action;
    private String code;
    private String userid;
    private String token;
    private String isshower;
    private String isexist;
    private Info info;

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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIsshower() {
        return isshower;
    }

    public void setIsshower(String isshower) {
        this.isshower = isshower;
    }

    public String getIsexist() {
        return isexist;
    }

    public void setIsexist(String isexist) {
        this.isexist = isexist;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public static class Info implements Serializable{
        private String userid;
        private String clanid;
        private String agentid;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getClanid() {
            return clanid;
        }

        public void setClanid(String clanid) {
            this.clanid = clanid;
        }

        public String getAgentid() {
            return agentid;
        }

        public void setAgentid(String agentid) {
            this.agentid = agentid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsernumber() {
            return usernumber;
        }

        public void setUsernumber(String usernumber) {
            this.usernumber = usernumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }

        public String getAccountfrom() {
            return accountfrom;
        }

        public void setAccountfrom(String accountfrom) {
            this.accountfrom = accountfrom;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getRegtime() {
            return regtime;
        }

        public void setRegtime(String regtime) {
            this.regtime = regtime;
        }

        public String getLastlogin() {
            return lastlogin;
        }

        public void setLastlogin(String lastlogin) {
            this.lastlogin = lastlogin;
        }

        public String getLastloginip() {
            return lastloginip;
        }

        public void setLastloginip(String lastloginip) {
            this.lastloginip = lastloginip;
        }

        public String getTotalpoint() {
            return totalpoint;
        }

        public void setTotalpoint(String totalpoint) {
            this.totalpoint = totalpoint;
        }

        public String getTotalcost() {
            return totalcost;
        }

        public void setTotalcost(String totalcost) {
            this.totalcost = totalcost;
        }

        public String getCanfindpassword() {
            return canfindpassword;
        }

        public void setCanfindpassword(String canfindpassword) {
            this.canfindpassword = canfindpassword;
        }

        public String getIsshowing() {
            return isshowing;
        }

        public void setIsshowing(String isshowing) {
            this.isshowing = isshowing;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getTotalshowtime() {
            return totalshowtime;
        }

        public void setTotalshowtime(String totalshowtime) {
            this.totalshowtime = totalshowtime;
        }

        public String getIsblock() {
            return isblock;
        }

        public void setIsblock(String isblock) {
            this.isblock = isblock;
        }

        public String getMedalvalid() {
            return medalvalid;
        }

        public void setMedalvalid(String medalvalid) {
            this.medalvalid = medalvalid;
        }

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public String getClanactor() {
            return clanactor;
        }

        public void setClanactor(String clanactor) {
            this.clanactor = clanactor;
        }

        public String getSnsid() {
            return snsid;
        }

        public void setSnsid(String snsid) {
            this.snsid = snsid;
        }

        public String getGamemoney() {
            return gamemoney;
        }

        public void setGamemoney(String gamemoney) {
            this.gamemoney = gamemoney;
        }

        public String getRoom_admin() {
            return room_admin;
        }

        public void setRoom_admin(String room_admin) {
            this.room_admin = room_admin;
        }

        public String getUpload_cover() {
            return upload_cover;
        }

        public void setUpload_cover(String upload_cover) {
            this.upload_cover = upload_cover;
        }

        public String getViplevel() {
            return viplevel;
        }

        public void setViplevel(String viplevel) {
            this.viplevel = viplevel;
        }

        public String getVip_vailddate() {
            return vip_vailddate;
        }

        public void setVip_vailddate(String vip_vailddate) {
            this.vip_vailddate = vip_vailddate;
        }

        public String getYinshen() {
            return yinshen;
        }

        public void setYinshen(String yinshen) {
            this.yinshen = yinshen;
        }

        public String getYinshen_vailddate() {
            return yinshen_vailddate;
        }

        public void setYinshen_vailddate(String yinshen_vailddate) {
            this.yinshen_vailddate = yinshen_vailddate;
        }

        public String getFangti() {
            return fangti;
        }

        public void setFangti(String fangti) {
            this.fangti = fangti;
        }

        public String getFangti_vailddate() {
            return fangti_vailddate;
        }

        public void setFangti_vailddate(String fangti_vailddate) {
            this.fangti_vailddate = fangti_vailddate;
        }

        public String getFeiniao() {
            return feiniao;
        }

        public void setFeiniao(String feiniao) {
            this.feiniao = feiniao;
        }

        public String getFeiniao_vailddate() {
            return feiniao_vailddate;
        }

        public void setFeiniao_vailddate(String feiniao_vailddate) {
            this.feiniao_vailddate = feiniao_vailddate;
        }

        public String getFirst_order() {
            return first_order;
        }

        public void setFirst_order(String first_order) {
            this.first_order = first_order;
        }

        public String getXinren() {
            return xinren;
        }

        public void setXinren(String xinren) {
            this.xinren = xinren;
        }

        public String getXinren_vailddate() {
            return xinren_vailddate;
        }

        public void setXinren_vailddate(String xinren_vailddate) {
            this.xinren_vailddate = xinren_vailddate;
        }

        public String getFontsize() {
            return fontsize;
        }

        public void setFontsize(String fontsize) {
            this.fontsize = fontsize;
        }

        public String getFontfamily() {
            return fontfamily;
        }

        public void setFontfamily(String fontfamily) {
            this.fontfamily = fontfamily;
        }

        public String getFontweight() {
            return fontweight;
        }

        public void setFontweight(String fontweight) {
            this.fontweight = fontweight;
        }

        public String getFontstyle() {
            return fontstyle;
        }

        public void setFontstyle(String fontstyle) {
            this.fontstyle = fontstyle;
        }

        public String getFontcolor() {
            return fontcolor;
        }

        public void setFontcolor(String fontcolor) {
            this.fontcolor = fontcolor;
        }

        public String getContinuou_login() {
            return continuou_login;
        }

        public void setContinuou_login(String continuou_login) {
            this.continuou_login = continuou_login;
        }

        public String getXinshoulibao() {
            return xinshoulibao;
        }

        public void setXinshoulibao(String xinshoulibao) {
            this.xinshoulibao = xinshoulibao;
        }

        public String getIslianghao() {
            return islianghao;
        }

        public void setIslianghao(String islianghao) {
            this.islianghao = islianghao;
        }

        public String getToupiaonum() {
            return toupiaonum;
        }

        public void setToupiaonum(String toupiaonum) {
            this.toupiaonum = toupiaonum;
        }

        public String getMax_giftstore1() {
            return max_giftstore1;
        }

        public void setMax_giftstore1(String max_giftstore1) {
            this.max_giftstore1 = max_giftstore1;
        }

        public String getYinshen_use() {
            return yinshen_use;
        }

        public void setYinshen_use(String yinshen_use) {
            this.yinshen_use = yinshen_use;
        }

        public String getLahei() {
            return lahei;
        }

        public void setLahei(String lahei) {
            this.lahei = lahei;
        }

        private String nickname;
        private String username;
        private String usernumber;
        private String email;
        private String shengao;
        private String tizhong;
        private String dbzp;
        private String gxqm;
        private String birthday;
        private String gender;
        private String province;
        private String city;
        private String unionid;
        private String usertype;
        private String accountfrom;
        private String balance;
        private String point;
        private String regtime;
        private String lastlogin;
        private String lastloginip;
        private String totalpoint;
        private String totalcost;
        private String canfindpassword;
        private String isshowing;
        private String avatar;
        private String mobile;
        private String qq;
        private String totalshowtime;
        private String isblock;
        private String medalvalid;
        private String ordernum;
        private String clanactor;
        private String snsid;
        private String gamemoney;
        private String room_admin;
        private String upload_cover;
        private String viplevel;
        private String vip_vailddate;
        private String yinshen;
        private String yinshen_vailddate;
        private String fangti;
        private String fangti_vailddate;
        private String feiniao;
        private String feiniao_vailddate;
        private String first_order;
        private String xinren;
        private String xinren_vailddate;
        private String fontsize;
        private String fontfamily;
        private String fontweight;
        private String fontstyle;
        private String fontcolor;
        private String continuou_login;
        private String xinshoulibao;
        private String islianghao;
        private String toupiaonum;
        private String max_giftstore1;
        private String yinshen_use;
        private String lahei;
    }
}
