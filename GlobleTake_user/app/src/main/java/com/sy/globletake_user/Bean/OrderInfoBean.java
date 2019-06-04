package com.sy.globletake_user.Bean;

import java.util.List;

/**
 * Created by sunnetdesign3 on 2017/2/22.
 */
public class OrderInfoBean {

    /**
     * code : 1
     * message : 执行成功!
     * result : [{"trip":{"fdTripCreateTime":1488533217280,"fdTripStartCoordinates":22.499154,"passenger_fdPhone":"18277750576","fdVehicleTypeNum":"宝安出租车","fdTotalMileage":10013,"fdTriptype":1,"fdStatus":5,"fdTripNum":"201703031726573592","fdTripEndCoordinateslongitude":113.97423285414,"fdTripStartLocation":"深圳市南山大厦-北座","fdTripStartTime":1488533224410,"fdTripEndLocation":"世界之窗","fdTripPrice":3500,"fdId":"1488533217280PAINA5","fdTripEndCoordinates":22.536899510415,"passenger_fdIconUrl":"upload/userImg/dc187943-5630-45a8-801d-66a5d2420662.jpg","fdTripMileage":10013,"fdTripEndTime":1488533243072,"fdName":"1","fdTripPeople":1,"fdTripStartCoordinateslongitude":113.915128},"driver":{"fdDriverCode":"16595","fdEmail":"1184824553@qq.com","fdVehicleTypeNum":"宝安出租车","fdUserName":"李显友","fdIconUrl":"upload/userImg/607a55a4-2903-4271-8331-872cf531652d.jpg","fdPhone":"18277750576","fdAccount":"是滴是滴","fdDriverCheck":"1","fdDriverAvgSocre":4.9,"fdNickName":"的士速递","fdExpireDate":1546300800000,"fdDriverlatitude":22.499037,"fdDriveStatus":1,"vehicleType_fdName":"1","fdDriverSex":1,"fdId":"1486710028198CQGA7X","fdPassowrd":"e10adc3949ba59abbe56e057f20f883e","fdTelephone":"18277750576","identity":"36078114","fdDriverlongitude":113.915039,"fdAddress":"","vehicleType_fdId":"14841158408490A9RJ3"},"order":{"fdIs_driverscore":0,"fdOrderLastPrice":0,"fdId":"14885332194931IX826","fdOrderScore":5,"fdOrderTime":1488533219493,"fdOrderendTime":1488533243072,"fdOrderPrice":3500,"fdOrderPreferentialPrice":0,"fdOrderStatus":1,"fdOrderNum":"201703031726590569"},"vehicle":{"vehicleType_fdName":"1","fdEnVehicleName":"sss","fdEnVehiclecolor":"bai","fdId":"14866343354088HAMW9","fdVehiclecolor":"bai","fdLastTime":1489226218000,"fdCreateTime":1486634335408,"fdVehicleCompanyCode":"sss","fdVehicleNumber":"asss","fdVehicleName":"s","fdVehicleStatus":1}}]
     */

    private String code;
    private String message;
    /**
     * trip : {"fdTripCreateTime":1488533217280,"fdTripStartCoordinates":22.499154,"passenger_fdPhone":"18277750576","fdVehicleTypeNum":"宝安出租车","fdTotalMileage":10013,"fdTriptype":1,"fdStatus":5,"fdTripNum":"201703031726573592","fdTripEndCoordinateslongitude":113.97423285414,"fdTripStartLocation":"深圳市南山大厦-北座","fdTripStartTime":1488533224410,"fdTripEndLocation":"世界之窗","fdTripPrice":3500,"fdId":"1488533217280PAINA5","fdTripEndCoordinates":22.536899510415,"passenger_fdIconUrl":"upload/userImg/dc187943-5630-45a8-801d-66a5d2420662.jpg","fdTripMileage":10013,"fdTripEndTime":1488533243072,"fdName":"1","fdTripPeople":1,"fdTripStartCoordinateslongitude":113.915128}
     * driver : {"fdDriverCode":"16595","fdEmail":"1184824553@qq.com","fdVehicleTypeNum":"宝安出租车","fdUserName":"李显友","fdIconUrl":"upload/userImg/607a55a4-2903-4271-8331-872cf531652d.jpg","fdPhone":"18277750576","fdAccount":"是滴是滴","fdDriverCheck":"1","fdDriverAvgSocre":4.9,"fdNickName":"的士速递","fdExpireDate":1546300800000,"fdDriverlatitude":22.499037,"fdDriveStatus":1,"vehicleType_fdName":"1","fdDriverSex":1,"fdId":"1486710028198CQGA7X","fdPassowrd":"e10adc3949ba59abbe56e057f20f883e","fdTelephone":"18277750576","identity":"36078114","fdDriverlongitude":113.915039,"fdAddress":"","vehicleType_fdId":"14841158408490A9RJ3"}
     * order : {"fdIs_driverscore":0,"fdOrderLastPrice":0,"fdId":"14885332194931IX826","fdOrderScore":5,"fdOrderTime":1488533219493,"fdOrderendTime":1488533243072,"fdOrderPrice":3500,"fdOrderPreferentialPrice":0,"fdOrderStatus":1,"fdOrderNum":"201703031726590569"}
     * vehicle : {"vehicleType_fdName":"1","fdEnVehicleName":"sss","fdEnVehiclecolor":"bai","fdId":"14866343354088HAMW9","fdVehiclecolor":"bai","fdLastTime":1489226218000,"fdCreateTime":1486634335408,"fdVehicleCompanyCode":"sss","fdVehicleNumber":"asss","fdVehicleName":"s","fdVehicleStatus":1}
     */

    private List<ResultBean> result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * fdTripCreateTime : 1488533217280
         * fdTripStartCoordinates : 22.499154
         * passenger_fdPhone : 18277750576
         * fdVehicleTypeNum : 宝安出租车
         * fdTotalMileage : 10013
         * fdTriptype : 1
         * fdStatus : 5
         * fdTripNum : 201703031726573592
         * fdTripEndCoordinateslongitude : 113.97423285414
         * fdTripStartLocation : 深圳市南山大厦-北座
         * fdTripStartTime : 1488533224410
         * fdTripEndLocation : 世界之窗
         * fdTripPrice : 3500
         * fdId : 1488533217280PAINA5
         * fdTripEndCoordinates : 22.536899510415
         * passenger_fdIconUrl : upload/userImg/dc187943-5630-45a8-801d-66a5d2420662.jpg
         * fdTripMileage : 10013
         * fdTripEndTime : 1488533243072
         * fdName : 1
         * fdTripPeople : 1
         * fdTripStartCoordinateslongitude : 113.915128
         */

        private TripBean trip;
        /**
         * fdDriverCode : 16595
         * fdEmail : 1184824553@qq.com
         * fdVehicleTypeNum : 宝安出租车
         * fdUserName : 李显友
         * fdIconUrl : upload/userImg/607a55a4-2903-4271-8331-872cf531652d.jpg
         * fdPhone : 18277750576
         * fdAccount : 是滴是滴
         * fdDriverCheck : 1
         * fdDriverAvgSocre : 4.9
         * fdNickName : 的士速递
         * fdExpireDate : 1546300800000
         * fdDriverlatitude : 22.499037
         * fdDriveStatus : 1
         * vehicleType_fdName : 1
         * fdDriverSex : 1
         * fdId : 1486710028198CQGA7X
         * fdPassowrd : e10adc3949ba59abbe56e057f20f883e
         * fdTelephone : 18277750576
         * identity : 36078114
         * fdDriverlongitude : 113.915039
         * fdAddress :
         * vehicleType_fdId : 14841158408490A9RJ3
         */

        private DriverBean driver;
        /**
         * fdIs_driverscore : 0
         * fdOrderLastPrice : 0
         * fdId : 14885332194931IX826
         * fdOrderScore : 5
         * fdOrderTime : 1488533219493
         * fdOrderendTime : 1488533243072
         * fdOrderPrice : 3500
         * fdOrderPreferentialPrice : 0
         * fdOrderStatus : 1
         * fdOrderNum : 201703031726590569
         */

        private OrderBean order;
        /**
         * vehicleType_fdName : 1
         * fdEnVehicleName : sss
         * fdEnVehiclecolor : bai
         * fdId : 14866343354088HAMW9
         * fdVehiclecolor : bai
         * fdLastTime : 1489226218000
         * fdCreateTime : 1486634335408
         * fdVehicleCompanyCode : sss
         * fdVehicleNumber : asss
         * fdVehicleName : s
         * fdVehicleStatus : 1
         */

        private VehicleBean vehicle;

        public TripBean getTrip() {
            return trip;
        }

        public void setTrip(TripBean trip) {
            this.trip = trip;
        }

        public DriverBean getDriver() {
            return driver;
        }

        public void setDriver(DriverBean driver) {
            this.driver = driver;
        }

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public VehicleBean getVehicle() {
            return vehicle;
        }

        public void setVehicle(VehicleBean vehicle) {
            this.vehicle = vehicle;
        }

        public static class TripBean {
            private long fdTripCreateTime;
            private double fdTripStartCoordinates;
            private String passenger_fdPhone;
            private String fdVehicleTypeNum;
            private String fdTotalMileage;
            private String fdTriptype;
            private String fdStatus;
            private String fdTripNum;
            private double fdTripEndCoordinateslongitude;
            private String fdTripStartLocation;
            private long fdTripStartTime;
            private String fdTripEndLocation;
            private String fdTripPrice;
            private String fdId;
            private double fdTripEndCoordinates;
            private String passenger_fdIconUrl;
            private String fdTripMileage;
            private long fdTripEndTime;
            private String fdName;
            private String fdTripPeople;
            private double fdTripStartCoordinateslongitude;

            public long getFdTripCreateTime() {
                return fdTripCreateTime;
            }

            public void setFdTripCreateTime(long fdTripCreateTime) {
                this.fdTripCreateTime = fdTripCreateTime;
            }

            public double getFdTripStartCoordinates() {
                return fdTripStartCoordinates;
            }

            public void setFdTripStartCoordinates(double fdTripStartCoordinates) {
                this.fdTripStartCoordinates = fdTripStartCoordinates;
            }

            public String getPassenger_fdPhone() {
                return passenger_fdPhone;
            }

            public void setPassenger_fdPhone(String passenger_fdPhone) {
                this.passenger_fdPhone = passenger_fdPhone;
            }

            public String getFdVehicleTypeNum() {
                return fdVehicleTypeNum;
            }

            public void setFdVehicleTypeNum(String fdVehicleTypeNum) {
                this.fdVehicleTypeNum = fdVehicleTypeNum;
            }

            public String getFdTotalMileage() {
                return fdTotalMileage;
            }

            public void setFdTotalMileage(String fdTotalMileage) {
                this.fdTotalMileage = fdTotalMileage;
            }

            public String getFdTriptype() {
                return fdTriptype;
            }

            public void setFdTriptype(String fdTriptype) {
                this.fdTriptype = fdTriptype;
            }

            public String getFdStatus() {
                return fdStatus;
            }

            public void setFdStatus(String fdStatus) {
                this.fdStatus = fdStatus;
            }

            public String getFdTripNum() {
                return fdTripNum;
            }

            public void setFdTripNum(String fdTripNum) {
                this.fdTripNum = fdTripNum;
            }

            public double getFdTripEndCoordinateslongitude() {
                return fdTripEndCoordinateslongitude;
            }

            public void setFdTripEndCoordinateslongitude(double fdTripEndCoordinateslongitude) {
                this.fdTripEndCoordinateslongitude = fdTripEndCoordinateslongitude;
            }

            public String getFdTripStartLocation() {
                return fdTripStartLocation;
            }

            public void setFdTripStartLocation(String fdTripStartLocation) {
                this.fdTripStartLocation = fdTripStartLocation;
            }

            public long getFdTripStartTime() {
                return fdTripStartTime;
            }

            public void setFdTripStartTime(long fdTripStartTime) {
                this.fdTripStartTime = fdTripStartTime;
            }

            public String getFdTripEndLocation() {
                return fdTripEndLocation;
            }

            public void setFdTripEndLocation(String fdTripEndLocation) {
                this.fdTripEndLocation = fdTripEndLocation;
            }

            public String getFdTripPrice() {
                return fdTripPrice;
            }

            public void setFdTripPrice(String fdTripPrice) {
                this.fdTripPrice = fdTripPrice;
            }

            public String getFdId() {
                return fdId;
            }

            public void setFdId(String fdId) {
                this.fdId = fdId;
            }

            public double getFdTripEndCoordinates() {
                return fdTripEndCoordinates;
            }

            public void setFdTripEndCoordinates(double fdTripEndCoordinates) {
                this.fdTripEndCoordinates = fdTripEndCoordinates;
            }

            public String getPassenger_fdIconUrl() {
                return passenger_fdIconUrl;
            }

            public void setPassenger_fdIconUrl(String passenger_fdIconUrl) {
                this.passenger_fdIconUrl = passenger_fdIconUrl;
            }

            public String getFdTripMileage() {
                return fdTripMileage;
            }

            public void setFdTripMileage(String fdTripMileage) {
                this.fdTripMileage = fdTripMileage;
            }

            public long getFdTripEndTime() {
                return fdTripEndTime;
            }

            public void setFdTripEndTime(long fdTripEndTime) {
                this.fdTripEndTime = fdTripEndTime;
            }

            public String getFdName() {
                return fdName;
            }

            public void setFdName(String fdName) {
                this.fdName = fdName;
            }

            public String getFdTripPeople() {
                return fdTripPeople;
            }

            public void setFdTripPeople(String fdTripPeople) {
                this.fdTripPeople = fdTripPeople;
            }

            public double getFdTripStartCoordinateslongitude() {
                return fdTripStartCoordinateslongitude;
            }

            public void setFdTripStartCoordinateslongitude(double fdTripStartCoordinateslongitude) {
                this.fdTripStartCoordinateslongitude = fdTripStartCoordinateslongitude;
            }
        }

        public static class DriverBean {
            private String fdDriverCode;
            private String fdEmail;
            private String fdVehicleTypeNum;
            private String fdUserName;
            private String fdIconUrl;
            private String fdPhone;
            private String fdAccount;
            private String fdDriverCheck;
            private double fdDriverAvgSocre;
            private String fdNickName;
            private long fdExpireDate;
            private double fdDriverlatitude;
            private String fdDriveStatus;
            private String vehicleType_fdName;
            private String fdDriverSex;
            private String fdId;
            private String fdPassowrd;
            private String fdTelephone;
            private String identity;
            private double fdDriverlongitude;
            private String fdAddress;
            private String vehicleType_fdId;

            public String getFdDriverCode() {
                return fdDriverCode;
            }

            public void setFdDriverCode(String fdDriverCode) {
                this.fdDriverCode = fdDriverCode;
            }

            public String getFdEmail() {
                return fdEmail;
            }

            public void setFdEmail(String fdEmail) {
                this.fdEmail = fdEmail;
            }

            public String getFdVehicleTypeNum() {
                return fdVehicleTypeNum;
            }

            public void setFdVehicleTypeNum(String fdVehicleTypeNum) {
                this.fdVehicleTypeNum = fdVehicleTypeNum;
            }

            public String getFdUserName() {
                return fdUserName;
            }

            public void setFdUserName(String fdUserName) {
                this.fdUserName = fdUserName;
            }

            public String getFdIconUrl() {
                return fdIconUrl;
            }

            public void setFdIconUrl(String fdIconUrl) {
                this.fdIconUrl = fdIconUrl;
            }

            public String getFdPhone() {
                return fdPhone;
            }

            public void setFdPhone(String fdPhone) {
                this.fdPhone = fdPhone;
            }

            public String getFdAccount() {
                return fdAccount;
            }

            public void setFdAccount(String fdAccount) {
                this.fdAccount = fdAccount;
            }

            public String getFdDriverCheck() {
                return fdDriverCheck;
            }

            public void setFdDriverCheck(String fdDriverCheck) {
                this.fdDriverCheck = fdDriverCheck;
            }

            public double getFdDriverAvgSocre() {
                return fdDriverAvgSocre;
            }

            public void setFdDriverAvgSocre(double fdDriverAvgSocre) {
                this.fdDriverAvgSocre = fdDriverAvgSocre;
            }

            public String getFdNickName() {
                return fdNickName;
            }

            public void setFdNickName(String fdNickName) {
                this.fdNickName = fdNickName;
            }

            public long getFdExpireDate() {
                return fdExpireDate;
            }

            public void setFdExpireDate(long fdExpireDate) {
                this.fdExpireDate = fdExpireDate;
            }

            public double getFdDriverlatitude() {
                return fdDriverlatitude;
            }

            public void setFdDriverlatitude(double fdDriverlatitude) {
                this.fdDriverlatitude = fdDriverlatitude;
            }

            public String getFdDriveStatus() {
                return fdDriveStatus;
            }

            public void setFdDriveStatus(String fdDriveStatus) {
                this.fdDriveStatus = fdDriveStatus;
            }

            public String getVehicleType_fdName() {
                return vehicleType_fdName;
            }

            public void setVehicleType_fdName(String vehicleType_fdName) {
                this.vehicleType_fdName = vehicleType_fdName;
            }

            public String getFdDriverSex() {
                return fdDriverSex;
            }

            public void setFdDriverSex(String fdDriverSex) {
                this.fdDriverSex = fdDriverSex;
            }

            public String getFdId() {
                return fdId;
            }

            public void setFdId(String fdId) {
                this.fdId = fdId;
            }

            public String getFdPassowrd() {
                return fdPassowrd;
            }

            public void setFdPassowrd(String fdPassowrd) {
                this.fdPassowrd = fdPassowrd;
            }

            public String getFdTelephone() {
                return fdTelephone;
            }

            public void setFdTelephone(String fdTelephone) {
                this.fdTelephone = fdTelephone;
            }

            public String getIdentity() {
                return identity;
            }

            public void setIdentity(String identity) {
                this.identity = identity;
            }

            public double getFdDriverlongitude() {
                return fdDriverlongitude;
            }

            public void setFdDriverlongitude(double fdDriverlongitude) {
                this.fdDriverlongitude = fdDriverlongitude;
            }

            public String getFdAddress() {
                return fdAddress;
            }

            public void setFdAddress(String fdAddress) {
                this.fdAddress = fdAddress;
            }

            public String getVehicleType_fdId() {
                return vehicleType_fdId;
            }

            public void setVehicleType_fdId(String vehicleType_fdId) {
                this.vehicleType_fdId = vehicleType_fdId;
            }
        }

        public static class OrderBean {
            private String fdIs_driverscore;
            private String fdOrderLastPrice;
            private String fdId;
            private String fdOrderScore;
            private long fdOrderTime;
            private long fdOrderendTime;
            private String fdOrderPrice;
            private String fdOrderPreferentialPrice;
            private String fdOrderStatus;
            private String fdOrderNum;

            public String getFdIs_driverscore() {
                return fdIs_driverscore;
            }

            public void setFdIs_driverscore(String fdIs_driverscore) {
                this.fdIs_driverscore = fdIs_driverscore;
            }

            public String getFdOrderLastPrice() {
                return fdOrderLastPrice;
            }

            public void setFdOrderLastPrice(String fdOrderLastPrice) {
                this.fdOrderLastPrice = fdOrderLastPrice;
            }

            public String getFdId() {
                return fdId;
            }

            public void setFdId(String fdId) {
                this.fdId = fdId;
            }

            public String getFdOrderScore() {
                return fdOrderScore;
            }

            public void setFdOrderScore(String fdOrderScore) {
                this.fdOrderScore = fdOrderScore;
            }

            public long getFdOrderTime() {
                return fdOrderTime;
            }

            public void setFdOrderTime(long fdOrderTime) {
                this.fdOrderTime = fdOrderTime;
            }

            public long getFdOrderendTime() {
                return fdOrderendTime;
            }

            public void setFdOrderendTime(long fdOrderendTime) {
                this.fdOrderendTime = fdOrderendTime;
            }

            public String getFdOrderPrice() {
                return fdOrderPrice;
            }

            public void setFdOrderPrice(String fdOrderPrice) {
                this.fdOrderPrice = fdOrderPrice;
            }

            public String getFdOrderPreferentialPrice() {
                return fdOrderPreferentialPrice;
            }

            public void setFdOrderPreferentialPrice(String fdOrderPreferentialPrice) {
                this.fdOrderPreferentialPrice = fdOrderPreferentialPrice;
            }

            public String getFdOrderStatus() {
                return fdOrderStatus;
            }

            public void setFdOrderStatus(String fdOrderStatus) {
                this.fdOrderStatus = fdOrderStatus;
            }

            public String getFdOrderNum() {
                return fdOrderNum;
            }

            public void setFdOrderNum(String fdOrderNum) {
                this.fdOrderNum = fdOrderNum;
            }
        }

        public static class VehicleBean {
            private String vehicleType_fdName;
            private String fdEnVehicleName;
            private String fdEnVehiclecolor;
            private String fdId;
            private String fdVehiclecolor;
            private long fdLastTime;
            private long fdCreateTime;
            private String fdVehicleCompanyCode;
            private String fdVehicleNumber;
            private String fdVehicleName;
            private String fdVehicleStatus;

            public String getVehicleType_fdName() {
                return vehicleType_fdName;
            }

            public void setVehicleType_fdName(String vehicleType_fdName) {
                this.vehicleType_fdName = vehicleType_fdName;
            }

            public String getFdEnVehicleName() {
                return fdEnVehicleName;
            }

            public void setFdEnVehicleName(String fdEnVehicleName) {
                this.fdEnVehicleName = fdEnVehicleName;
            }

            public String getFdEnVehiclecolor() {
                return fdEnVehiclecolor;
            }

            public void setFdEnVehiclecolor(String fdEnVehiclecolor) {
                this.fdEnVehiclecolor = fdEnVehiclecolor;
            }

            public String getFdId() {
                return fdId;
            }

            public void setFdId(String fdId) {
                this.fdId = fdId;
            }

            public String getFdVehiclecolor() {
                return fdVehiclecolor;
            }

            public void setFdVehiclecolor(String fdVehiclecolor) {
                this.fdVehiclecolor = fdVehiclecolor;
            }

            public long getFdLastTime() {
                return fdLastTime;
            }

            public void setFdLastTime(long fdLastTime) {
                this.fdLastTime = fdLastTime;
            }

            public long getFdCreateTime() {
                return fdCreateTime;
            }

            public void setFdCreateTime(long fdCreateTime) {
                this.fdCreateTime = fdCreateTime;
            }

            public String getFdVehicleCompanyCode() {
                return fdVehicleCompanyCode;
            }

            public void setFdVehicleCompanyCode(String fdVehicleCompanyCode) {
                this.fdVehicleCompanyCode = fdVehicleCompanyCode;
            }

            public String getFdVehicleNumber() {
                return fdVehicleNumber;
            }

            public void setFdVehicleNumber(String fdVehicleNumber) {
                this.fdVehicleNumber = fdVehicleNumber;
            }

            public String getFdVehicleName() {
                return fdVehicleName;
            }

            public void setFdVehicleName(String fdVehicleName) {
                this.fdVehicleName = fdVehicleName;
            }

            public String getFdVehicleStatus() {
                return fdVehicleStatus;
            }

            public void setFdVehicleStatus(String fdVehicleStatus) {
                this.fdVehicleStatus = fdVehicleStatus;
            }
        }
    }
}
