package com.sy.globletake_user.Other;

/**
 * Created by sunnetdesign3 on 2017/2/7.
 */
public class Const {
    public static final String IS_FIRST = "is_first";
    //请求响应返回码字段
    public static final String Code = "code";
    //请求成功
    public static final String reques_success = "1";
    public static final String EXTRA_BUNDLE = "extra_bundle";
    public static final String Language = "language";
    public static final String Chinese = "zh";
    public static final String English = "english";
    public static final String zh = "zh";
    public static final String en = "en";
    public static final String Driving = "DRIVING";//驾车（出租车）
    public static final String Moto = "BICYCLING";//摩托车
    public static final String WALKING = "WALKING";//步行
    public static final String waiterPhone = "waiterPhone";//步行

    /*计价规则参数留存*/
    //出租车
    public static final String startingfare = "startingfare";//起步价
    public static final String journey = "journey";//里程
    public static final String meter = "meter";//米数
    public static final String moneybymeter = "moneybymeter";//多少钱每多少米
    public static final String moneybyminute = "moneybyminute";//多少钱每分钟

    //摩托车
    public static final String startingfare_moto = "startingfare_moto";//起步价
    public static final String journey_moto = "journey_moto";//里程
    public static final String meter_moto = "meter_moto";//米数
    public static final String moneybymeter_moto = "moneybymeter_moto";//多少钱每多少米
    public static final String moneybyminute_moto = "moneybyminute_moto";//多少钱每分钟

    /*缓存文件名*/
    //用户手机号
    public static final String isLogin = "isLogin";
    //用户手机号
    public static final String USER_PHONE = "user_phone";
    //用户输入的密码（加密前）
    public static final String USER_PWD = "user_pwd";
    //用户Email
    public static final String User_Email = "User_Email";
    //用户ID
    public static final String UserId = "user_id";
    //用户fdPassengerCode
    public static final String fdPassengerCode = "fdPassengerCode";
    //用户fdPassengerSex
    public static final String fdPassengerSex = "fdPassengerSex";
    //用户密码（加密后）
    public static final String fdPassowrd = "fdPassowrd";
    //用户昵称
    public static final String User_NickName = "User_NickName";
    //用户名
    public static final String User_Name = "User_Name";
    //用户xx状态
    public static final String User_Stauts = "User_Stauts";
    //用户头像地址
    public static final String User_HeadIcon = "User_HeadIcon";
    //用户行程状态
    public static final String TripStatus = "TripStatus";
    //叫车类型
    public static final String CarType = "CarType";
    //叫车类型
    public static final String CarType_taxi = "1";//1:出租车；2：摩托车
    public static final String CarType_Moto = "2";//1:出租车；2：摩托车

    public static final int GO_GUIDE=100;
    public static final int GO_MAIN=101;
    public static final int SPLASH_DELAY_TIME=1000;//闪屏时间


    /*根域名*/
//    public static final String root_url = "http://120.24.234.123:8080/";//注意必须加上http://
    public static final String root_url = "http://www.global-take.com/";//注意必须加上http://
    //用户登录
    public static final String user_login = root_url+"api/member/login";
    //用户注册
    public static final String user_register = root_url+"api/member/register";
    //获取验证码
    public static final String getCode = root_url+"api/member/usercode";
    //重置密码
    public static final String resetPWD = root_url+"api/member/updatePassword";
    //修改头像
    public static final String changeHead = root_url+"api/member/updateheadPortrait";
    //修改其他信息
    public static final String changeOtherInfo = root_url+"api/member/updatememberinfo";
    //退出登录
    public static final String exitLogin = root_url+"api/member/gooutuser";
    //路线规划
    public static final String polylines_url = "https://maps.googleapis.com/maps/api/directions/json";
    //获取参考价
    public static final String get_recommendPrice = root_url+"/api/trip/givemycarmoney";
    //发布行程
    public static final String callCars = root_url+"api/trip/fabutrip";
    //取消行程
    public static final String cancleTrip = root_url+"api/trip/endtrip";
    //订单详情
    public static final String getOrderInfo = root_url+"api/order/orderinformation";
    //提交评价
    public static final String submitevaluate = root_url+"api/order/submitevaluate";
    //出租车计价规则
    public static final String taxiPriceRule = root_url+"api/trip/valuationrule";
    //摩托车计价规则
    public static final String motoPriceRule = root_url+"api/trip/valuationrulebymo";
    //获取附近车辆
    public static final String getAreaCar = root_url+"api/trip/selectnearbycar";
    //获取常用地址
    public static final String getMyAddress = root_url+"api/member/alwaysAdress";
    //获取搜索页icon
    public static final String getIcons = root_url+"/api/classification/giveClassification";
    //添加地址
    public static final String addMyAddress = root_url+"api/symboliadress/savesymbolicAddress";
    //获取客服电话
    public static final String getWaiterPhonenumber = root_url+"api/trip/givephone";
    //获取面包车数据
    public static final String getMianBaoCar = root_url+"api/trip/minibus";
    //获取长途客车数据
    public static final String getBashiCar = root_url+"api/trip/busInformation";

    //投诉与反馈
    public static final String tousu = root_url+"api/member/myfeedback";

    //获取我的行程
    public static final String getMyTrip = root_url+"api/trip/mytrip";
    //获取行程详细信息
    public static final String getGetTripInfo = root_url+"/api/trip/tripInfobytripid";
    //修改手机号
    public static final String changeCellPhone = root_url+"api/member/updatephone";
    //获取参考地址分类列表
    public static final String getClassfylist = root_url+"api/classification/giveaddressbyclass";

    //帮助中心，关于，注册协议
    public static final String webRootUrl = root_url+"api/helpinfo/list?language=zh&key=";

    public static final String KeyRegister="1487140108418E7PB31";//注册协议的key
    public static final String KeyHelp="148714074211464W5FH";//帮助中心的key
    public static final String KeyAbout="148714037641967KSPB";//关于我们的key

    //谷歌play服务
//    public static final String googlesevice = "http://www.miui.com/forum.php?mod=attachment&aid=NDM2MTYyMHxjNTBjMjA4OHwxNDkwMTY4MDU2fDIyMzE0NDYxOTh8NDE4NDQzMw%3D%3D&ck=532e35e4";
    public static final String googlesevice = "http://shouji.360tpcdn.com/170316/4b4094bdbf42b275ce190446aa06c524/com.goplaycn.googleinstall_15.apk";


}
