package com.sy.globletake_user.Bean;

import java.util.List;

/**
 * Created by sunnetdesign3 on 2017/3/17.
 */
public class ClassifyBean {

    /**
     * code : 1
     * message : 执行成功!
     * result : [{"classification_fdNameEn":"Public service","fdUserNameEn":"s","fdAddressCn":"金边俄罗斯大道","fdRegion":"金边机场","fdUserName":"S001s","classification_fdNameCn":"公共","fdPhone":"13000000000","fdStatus":1,"fdCoordinates":11.549748,"classification_fdId":"1489112011881JHJ0JH","fdId":"1489112404789JANU99","fdEndinates":104.844839,"fdAddressEn":"12000,, Angkor Phnom Penh St, Phnom Penh","fdUrl":"upload/address/50c79b1c-035c-4096-a340-3b1cd6200ec6.jpg"},{"classification_fdNameEn":"Public service","fdUserNameEn":"1","fdAddressCn":"金边莫尼旺大道","fdRegion":"甘密医院","fdUserName":"1","classification_fdNameCn":"公共","fdPhone":"13000000000","fdStatus":1,"fdCoordinates":11.5811,"classification_fdId":"1489112011881JHJ0JH","fdId":"14891129383385APPH9","fdEndinates":104.915861,"fdAddressEn":"St 80, Phnom Penh","fdUrl":"upload/address/18fed17e-6dab-4e78-8330-99e2ac57ba9b.jpg"},{"classification_fdNameEn":"Public service","fdUserNameEn":"1","fdAddressCn":"Preah Sotheavong, 90 Chivapol, Phnom Penh","fdRegion":"昆塔帕花儿童医院","fdUserName":"w","classification_fdNameCn":"公共","fdPhone":"13000000000","fdStatus":1,"fdCoordinates":11.577958,"classification_fdId":"1489112011881JHJ0JH","fdId":"1489195852127UY5DEH","fdEndinates":104.920917,"fdAddressEn":"Preah Sotheavong, 90 Chivapol, Phnom Penh","fdUrl":"upload/address/8f839a60-4035-4dbb-94c2-7daa20ee7e44.jpg"},{"classification_fdNameEn":"Public service","fdAddressCn":"271, Phnom Penh 12306","fdRegion":"苏联医院","classification_fdNameCn":"公共","fdPhone":"13000000000","fdStatus":1,"fdCoordinates":11.544912,"classification_fdId":"1489112011881JHJ0JH","fdId":"1489195994286WG2CZ9","fdEndinates":104.905066,"fdAddressEn":"271, Phnom Penh 12306","fdUrl":"upload/address/3c87fe47-0243-4a51-8964-1e2191d39fbe.jpg"}]
     */

    private String code;
    private String message;
    /**
     * classification_fdNameEn : Public service
     * fdUserNameEn : s
     * fdAddressCn : 金边俄罗斯大道
     * fdRegion : 金边机场
     * fdUserName : S001s
     * classification_fdNameCn : 公共
     * fdPhone : 13000000000
     * fdStatus : 1
     * fdCoordinates : 11.549748
     * classification_fdId : 1489112011881JHJ0JH
     * fdId : 1489112404789JANU99
     * fdEndinates : 104.844839
     * fdAddressEn : 12000,, Angkor Phnom Penh St, Phnom Penh
     * fdUrl : upload/address/50c79b1c-035c-4096-a340-3b1cd6200ec6.jpg
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
        private String classification_fdNameEn;
        private String fdUserNameEn;
        private String fdAddressCn;
        private String fdRegion;
        private String fdUserName;
        private String classification_fdNameCn;
        private String fdPhone;
        private int fdStatus;
        private double fdCoordinates;
        private String classification_fdId;
        private String fdId;
        private double fdEndinates;
        private String fdAddressEn;
        private String fdUrl;

        public String getClassification_fdNameEn() {
            return classification_fdNameEn;
        }

        public void setClassification_fdNameEn(String classification_fdNameEn) {
            this.classification_fdNameEn = classification_fdNameEn;
        }

        public String getFdUserNameEn() {
            return fdUserNameEn;
        }

        public void setFdUserNameEn(String fdUserNameEn) {
            this.fdUserNameEn = fdUserNameEn;
        }

        public String getFdAddressCn() {
            return fdAddressCn;
        }

        public void setFdAddressCn(String fdAddressCn) {
            this.fdAddressCn = fdAddressCn;
        }

        public String getFdRegion() {
            return fdRegion;
        }

        public void setFdRegion(String fdRegion) {
            this.fdRegion = fdRegion;
        }

        public String getFdUserName() {
            return fdUserName;
        }

        public void setFdUserName(String fdUserName) {
            this.fdUserName = fdUserName;
        }

        public String getClassification_fdNameCn() {
            return classification_fdNameCn;
        }

        public void setClassification_fdNameCn(String classification_fdNameCn) {
            this.classification_fdNameCn = classification_fdNameCn;
        }

        public String getFdPhone() {
            return fdPhone;
        }

        public void setFdPhone(String fdPhone) {
            this.fdPhone = fdPhone;
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

        public String getClassification_fdId() {
            return classification_fdId;
        }

        public void setClassification_fdId(String classification_fdId) {
            this.classification_fdId = classification_fdId;
        }

        public String getFdId() {
            return fdId;
        }

        public void setFdId(String fdId) {
            this.fdId = fdId;
        }

        public double getFdEndinates() {
            return fdEndinates;
        }

        public void setFdEndinates(double fdEndinates) {
            this.fdEndinates = fdEndinates;
        }

        public String getFdAddressEn() {
            return fdAddressEn;
        }

        public void setFdAddressEn(String fdAddressEn) {
            this.fdAddressEn = fdAddressEn;
        }

        public String getFdUrl() {
            return fdUrl;
        }

        public void setFdUrl(String fdUrl) {
            this.fdUrl = fdUrl;
        }
    }
}
