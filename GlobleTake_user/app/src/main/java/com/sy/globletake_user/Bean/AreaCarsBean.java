package com.sy.globletake_user.Bean;

import java.util.List;

/**
 * Created by sunnetdesign3 on 2017/3/14.
 */
public class AreaCarsBean {

    /**
     * code : 1
     * message : Execute successfully!
     * result : [{"fdDriverCode":"0101","fdUserName":"黄俊铨","fdIconUrl":"upload/userImg/de158dd6-424c-478f-a65c-faafdecfa1d1.jpg","fdPhone":"15570086963","fdDriverCheck":"1","fdStauts":1,"fdDriverAvgSocre":5,"fdExpireDate":2177452800000,"fdDriverlatitude":22.49899433927843,"fdDriveStatus":2,"vehicleType_fdName":"1","fdDriverSex":1,"fdId":"1484222155614JSUASM","identity":"360781199511090079","fdDriverlongitude":113.9150110167581,"fdAddress":"黄家驹s","vehicleType_FdVehicleTypeNum":"黄色车"},{"fdDriverCode":"16595","fdUserName":"李显友","fdIconUrl":"upload/userImg/179b253c-3e5b-44c5-870f-b62a64a9505f.jpg","fdPhone":"18277750576","fdDriverCheck":"1","fdStauts":1,"fdDriverAvgSocre":4.9,"fdExpireDate":1546300800000,"fdDriverlatitude":22.496028821458737,"fdDriveStatus":2,"vehicleType_fdName":"1","fdDriverSex":1,"fdId":"1486710028198CQGA7X","identity":"360781141","fdDriverlongitude":113.91993526703264,"fdAddress":"","vehicleType_FdVehicleTypeNum":"Global 自有车"},{"fdDriverCode":"003","fdUserName":"Fat","fdIconUrl":"upload/userImg/0bb926c8-4df7-4e16-a374-24ca54865bb1.jpg","fdPhone":"13010311888","fdDriverCheck":"1","fdStauts":1,"fdDriverAvgSocre":0,"fdExpireDate":1495670400000,"fdDriverlatitude":11.52668159456134,"fdDriveStatus":2,"vehicleType_fdName":"1","fdDriverSex":1,"fdId":"1489113543918QEOLT1","identity":"502080018","fdDriverlongitude":104.9208552484446,"fdAddress":"金边俄罗斯大道15号","vehicleType_FdVehicleTypeNum":"Global 挂靠车"},{"fdDriverCode":"288","fdUserName":"SOK","fdIconUrl":"upload/userImg/41da069f-9952-4d71-980b-6d338bc89cce.jpg","fdPhone":"13086888072","fdDriverCheck":"1","fdStauts":1,"fdDriverAvgSocre":5,"fdExpireDate":1495785802000,"fdDriverlatitude":11.569793333333333,"fdDriveStatus":2,"vehicleType_fdName":"1","fdDriverSex":1,"fdId":"1489392292931ME2NAY","identity":"123456789","fdDriverlongitude":104.915815,"fdAddress":"","vehicleType_FdVehicleTypeNum":"Global 挂靠车"}]
     */

    private String code;
    private String message;
    /**
     * fdDriverCode : 0101
     * fdUserName : 黄俊铨
     * fdIconUrl : upload/userImg/de158dd6-424c-478f-a65c-faafdecfa1d1.jpg
     * fdPhone : 15570086963
     * fdDriverCheck : 1
     * fdStauts : 1
     * fdDriverAvgSocre : 5
     * fdExpireDate : 2177452800000
     * fdDriverlatitude : 22.49899433927843
     * fdDriveStatus : 2
     * vehicleType_fdName : 1
     * fdDriverSex : 1
     * fdId : 1484222155614JSUASM
     * identity : 360781199511090079
     * fdDriverlongitude : 113.9150110167581
     * fdAddress : 黄家驹s
     * vehicleType_FdVehicleTypeNum : 黄色车
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
        private String fdDriverCode;
        private String fdUserName;
        private String fdIconUrl;
        private String fdPhone;
        private String fdDriverCheck;
        private int fdStauts;
        private double fdDriverAvgSocre;
        private long fdExpireDate;
        private double fdDriverlatitude;
        private int fdDriveStatus;
        private String vehicleType_fdName;
        private String fdDriverSex;
        private String fdId;
        private String identity;
        private double fdDriverlongitude;
        private String fdAddress;
        private String vehicleType_FdVehicleTypeNum;

        public String getFdDriverCode() {
            return fdDriverCode;
        }

        public void setFdDriverCode(String fdDriverCode) {
            this.fdDriverCode = fdDriverCode;
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

        public String getFdDriverCheck() {
            return fdDriverCheck;
        }

        public void setFdDriverCheck(String fdDriverCheck) {
            this.fdDriverCheck = fdDriverCheck;
        }

        public int getFdStauts() {
            return fdStauts;
        }

        public void setFdStauts(int fdStauts) {
            this.fdStauts = fdStauts;
        }

        public double getFdDriverAvgSocre() {
            return fdDriverAvgSocre;
        }

        public void setFdDriverAvgSocre(double fdDriverAvgSocre) {
            this.fdDriverAvgSocre = fdDriverAvgSocre;
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

        public int getFdDriveStatus() {
            return fdDriveStatus;
        }

        public void setFdDriveStatus(int fdDriveStatus) {
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

        public String getVehicleType_FdVehicleTypeNum() {
            return vehicleType_FdVehicleTypeNum;
        }

        public void setVehicleType_FdVehicleTypeNum(String vehicleType_FdVehicleTypeNum) {
            this.vehicleType_FdVehicleTypeNum = vehicleType_FdVehicleTypeNum;
        }
    }
}
