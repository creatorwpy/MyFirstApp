package com.wpy.xh.config;

/**
 * Created by harmy on 2016/8/4 0004.
 */
public class NetConfig {

    public static final String CODE_SUCCESS = "200";

    public static final String HOST = "http://www.258star.com/";

    public static final String iumobileapi = "iumobile/apis/?";
    public static final String iumobileapiindex = "iumobile/apis/index.php?";
    public static final String photoapi = "apis/photo_api.php?";
    public static final String wxpay = "apis/Wxpay2/example51543/nativePhone.php?";
    public static final String feedback = "ajax/feedback.php?";


    /**
     * 检查更新
     */
    public static final String app_update = "app_update";
    /**
     * 搜索action
     */
    public static final String search = "search";
    /**
     * 选秀列表action
     */
    public static final String xingxiu_list = "xingxiu_list";
    /**
     * 活动主题列表action
     */
    public static final String activity_theme = "activity_theme";
    /**
     * 广告列表action
     */
    public static final String get_ad = "get_ad";
    /**
     * 登录action
     */
    public static final String login = "login";
    /**
     * 第三方登录action
     */
    public static final String loginbysns = "loginbysns";
    /**
     * 获取用户信息action
     */
    public static final String getuserinfo = "getuserinfo";
    /**
     * 上传头像action
     */
    public static final String upavatar = "full";
    /**
     * 开始直播action
     */
    public static final String startshow = "startshow";
    /**
     * 结束直播action
     */
    public static final String endshow = "endshow";
    /**
     * 获取直播间用户列表action
     */
    public static final String getuserlist = "getuserlist";
    /**
     * 获取活动主题列表action
     */
    public static final String activity_theme_list = "activity_theme_list";
    /**
     * 获取直播间信息action
     */
    public static final String get_room_info = "get_room_info";
    /**
     * 主题/活动订阅action
     */
    public static final String activity_theme_dingyue = "activity_theme_dingyue";
    /**
     * 艺人相册action
     */
    public static final String yiren_info = "yiren_info";
    /**
     * 投票action
     */
    public static final String toupiaoBlance = "toupiaoBlance";
    /**
     * 程序崩溃日志action
     */
    public static final String debug = "debug";
    /**
     * 发表说说，上传图片action
     */
    public static final String upload = "upload";
    /**
     * 点赞action
     */
    public static final String zan = "zan";
    /**
     * 取消点赞action
     */
    public static final String zan_cancel = "zan_cancel";
    /**
     * 删除照片action
     */
    public static final String photodel = "photodel";
    /**
     * 支付宝action
     */
    public static final String alipay = "alipay";
    /**
     * 微信action
     */
    public static final String weixin = "weixin";
    /**
     * 意见反馈action
     */
    public static final String save = "save";
    /**
     * 编辑个人信息action
     */
    public static final String userinfo_edit = "userinfo_edit";
    /**
     * 关注取消关注
     */
    public static final String addfav = "addfav";
    /**
     * 关注列表
     */
    public static final String follow = "follow";
    /**
     * 粉丝列表
     */
    public static final String follow_me = "follow_me";
    /**
     * 忘记密码获取验证码列表
     */
    public static final String Retrieve_getcode = "Retrieve_getcode";
    /**
     * 注册账号获取验证码列表
     */
    public static final String phone_message = "phone_message";
    /**
     * 修改密码
     */
    public static final String Retrieve_password = "Retrieve_password";
    /**
     * 注册
     */
    public static final String phone_register = "phone_register";
    /**
     * 注册验证手机号和验证码
     */
    public static final String phone_message_code = "phone_message_code";
    /**
     * 礼物列表
     */
    public static final String all_gifts = "all_gifts";
    /**
     * 举报action
     */
    public static final String jubao = "jubao";


    private static final String THEMEPICURL = HOST+"images/Activity_theme/";

    public static String getThemePic(String pic) {
        String url = THEMEPICURL;
        return url + pic;
    }

    public static String getThemePic(String pic, int width, int height) {
        String url = THEMEPICURL;
        String wh = "thumb" + width + "x" + height + "_";
        return url + wh + pic;
    }

    private static final String XINGXIUPICURL = HOST+"static_data/showcover/";

    public static String getXingxiuPic(String pic) {
        String url = XINGXIUPICURL;
        return url + pic;
    }

    public static String getXingxiuPic(String pic, int width, int height) {
        String url = XINGXIUPICURL;
        String wh = "thumb" + width + "x" + height + "_";
        return url + wh + pic;
    }

    private static final String XINGXIUPICURL_PHOTO = HOST+"static_data/uploaddata/photo/";

    public static String getXingxiuphotoPic(String pic) {
        String url = XINGXIUPICURL_PHOTO;
        return url + pic;
    }

    public static String getXingxiuphotoPic(String pic, int width, int height) {
        String url = XINGXIUPICURL_PHOTO;
        String wh = "thumb" + width + "x" + height + "_";
        return url + wh + pic;
    }

    private static final String GIFTURL = HOST+"static_data/gift/";

    public static String getGift(String pic) {
        String url = GIFTURL;
        return url + pic;
    }

    public static String getGift(String pic, int width, int height) {
        String url = GIFTURL;
        String wh = "thumb" + width + "x" + height + "_";
        return url + wh + pic;
    }
    private static final String SHOWGIFTURL = HOST+"static_data/showGift/mobile/";

    public static String getShowGift(String pic) {
        String url = SHOWGIFTURL;
        return url + pic;
    }

    private static final String AVATARURL = HOST+"apis/avatar.php?uid=";

    public static String getAvatar(String userid, String tag) {
        String url = AVATARURL;
        return url + userid + "&" + tag;
    }


    private static final String CARURL = HOST+"static_data/car/mobile/";

    public static String getCar(String pic) {
        String url = CARURL;
        return url + pic;
    }
}
