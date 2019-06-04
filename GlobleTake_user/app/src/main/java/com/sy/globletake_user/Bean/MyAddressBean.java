package com.sy.globletake_user.Bean;

import java.util.List;

/**
 * Created by sunnetdesign3 on 2017/3/1.
 */
public class MyAddressBean {

    /**
     * code : 1
     * message : 执行成功!
     * result : [{"userfdId":"1484709390161RBLXPJ","fdId":"1484709567414DT27ZH","fdUserName":"哈哈","fdStatus":1,"fdCoordinates":22.49584,"fdName":"南山大厦","fdAddress":"中国广东省深圳市南山区南海大道1069号","fdType":1,"fdlongitudeCoordinates":113.9196},{"userfdId":"1484709390161RBLXPJ","fdId":"1484709578164O2WXOJ","fdUserName":"哈哈","fdStatus":1,"fdCoordinates":22.49584,"fdName":"南山大厦","fdAddress":"中国广东省深圳市南山区南海大道1069号","fdType":2,"fdlongitudeCoordinates":113.9196}]
     */

    private String code;
    private String message;
    /**
     * userfdId : 1484709390161RBLXPJ
     * fdId : 1484709567414DT27ZH
     * fdUserName : 哈哈
     * fdStatus : 1
     * fdCoordinates : 22.49584
     * fdName : 南山大厦
     * fdAddress : 中国广东省深圳市南山区南海大道1069号
     * fdType : 1
     * fdlongitudeCoordinates : 113.9196
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
        private String userfdId;
        private String fdId;
        private String fdUserName;
        private int fdStatus;
        private double fdCoordinates;
        private String fdName;
        private String fdAddress;
        private int fdType;
        private double fdlongitudeCoordinates;

        public String getUserfdId() {
            return userfdId;
        }

        public void setUserfdId(String userfdId) {
            this.userfdId = userfdId;
        }

        public String getFdId() {
            return fdId;
        }

        public void setFdId(String fdId) {
            this.fdId = fdId;
        }

        public String getFdUserName() {
            return fdUserName;
        }

        public void setFdUserName(String fdUserName) {
            this.fdUserName = fdUserName;
        }

        public int getFdStatus() {
            return fdStatus;
        }

        public void setFdStatus(int fdStatus) {
            this.fdStatus = fdStatus;
        }

        public double getFdCoordinates() {
            return fdCoordinates;
        }

        public void setFdCoordinates(double fdCoordinates) {
            this.fdCoordinates = fdCoordinates;
        }

        public String getFdName() {
            return fdName;
        }

        public void setFdName(String fdName) {
            this.fdName = fdName;
        }

        public String getFdAddress() {
            return fdAddress;
        }

        public void setFdAddress(String fdAddress) {
            this.fdAddress = fdAddress;
        }

        public int getFdType() {
            return fdType;
        }

        public void setFdType(int fdType) {
            this.fdType = fdType;
        }

        public double getFdlongitudeCoordinates() {
            return fdlongitudeCoordinates;
        }

        public void setFdlongitudeCoordinates(double fdlongitudeCoordinates) {
            this.fdlongitudeCoordinates = fdlongitudeCoordinates;
        }
    }
}
