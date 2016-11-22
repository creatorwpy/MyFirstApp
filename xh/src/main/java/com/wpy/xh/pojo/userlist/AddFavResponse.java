package com.wpy.xh.pojo.userlist;

import com.wpy.xh.pojo.ResponseResult;

/**
 * Created by harmy on 2016/8/24 0024.
 */
public class AddFavResponse extends ResponseResult {
    private String fav;
    private String userid;
    private int myfav_num;
    private int favmy_num;
    private int haoyou_num;

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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
}
