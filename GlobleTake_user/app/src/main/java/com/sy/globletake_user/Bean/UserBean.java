package com.sy.globletake_user.Bean;

/**
 * Created by sunnetdesign3 on 2017/2/17.
 */
public class UserBean {

    /**
     * code : 1
     * message : 登录成功！
     * result : {"fdEmail":"601910545@qq.com","fdPassengerCode":"","fdPassengerSex":1,"fdId":"1484709390161RBLXPJ","fdPassowrd":"e10adc3949ba59abbe56e057f20f883e","fdTelephone":"18277750576","fdUserName":"哈哈","fdIconUrl":"upload/userImg/fffbcc37-559c-4f3d-83ae-9efec6973917.jpg","fdPhone":"18277750576","fdAccount":"18277750576","fdStauts":1,"fdNickName":""}
     */

    private String code;
    private String message;
    /**
     * fdEmail : 601910545@qq.com
     * fdPassengerCode :
     * fdPassengerSex : 1
     * fdId : 1484709390161RBLXPJ
     * fdPassowrd : e10adc3949ba59abbe56e057f20f883e
     * fdTelephone : 18277750576
     * fdUserName : 哈哈
     * fdIconUrl : upload/userImg/fffbcc37-559c-4f3d-83ae-9efec6973917.jpg
     * fdPhone : 18277750576
     * fdAccount : 18277750576
     * fdStauts : 1
     * fdNickName :
     */

    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private String fdEmail;
        private String fdPassengerCode;
        private String fdPassengerSex;
        private String fdId;
        private String fdPassowrd;
        private String fdTelephone;
        private String fdUserName;
        private String fdIconUrl;
        private String fdPhone;
        private String fdAccount;
        private int fdStauts;
        private String fdNickName;

        public String getFdEmail() {
            return fdEmail;
        }

        public void setFdEmail(String fdEmail) {
            this.fdEmail = fdEmail;
        }

        public String getFdPassengerCode() {
            return fdPassengerCode;
        }

        public void setFdPassengerCode(String fdPassengerCode) {
            this.fdPassengerCode = fdPassengerCode;
        }

        public String getFdPassengerSex() {
            return fdPassengerSex;
        }

        public void setFdPassengerSex(String fdPassengerSex) {
            this.fdPassengerSex = fdPassengerSex;
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

        public int getFdStauts() {
            return fdStauts;
        }

        public void setFdStauts(int fdStauts) {
            this.fdStauts = fdStauts;
        }

        public String getFdNickName() {
            return fdNickName;
        }

        public void setFdNickName(String fdNickName) {
            this.fdNickName = fdNickName;
        }
    }
}
