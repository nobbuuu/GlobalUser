package com.sy.globletake_user.Bean;

import java.util.List;

/**
 * Created by sunnetdesign3 on 2017/3/17.
 */
public class IconsBean {

    /**
     * code : 1
     * message : 执行成功！
     * result : [{"fdId":"148093478130504R3YV","fddUrl":"upload/address/f2b4cf4d-4b55-4da3-a8b5-b959e0e64968.jpg","fdNameEn":"Restaurant","fdStatus":3,"fdNameCn":"餐厅"},{"fdId":"1480988773589BUUQFJ","fddUrl":"upload/address/0ebdb6b1-c846-4ff9-8051-d278bdd706ee.jpg","fdNameEn":"Hotel","fdStatus":1,"fdNameCn":"住宿"},{"fdId":"1489112011881JHJ0JH","fddUrl":"upload/address/fde487f9-c10c-43d3-8bff-a207dd4ca6ca.jpg","fdNameEn":"Public service","fdStatus":1,"fdNameCn":"公共"},{"fdId":"1489219513020XMMA65","fddUrl":"upload/address/2da4ba80-247a-4883-a9e5-600735668a0a.jpg","fdNameEn":"Amusement","fdStatus":1,"fdNameCn":"娱乐"},{"fdId":"1489220138386UONBUR","fddUrl":"upload/address/4a44b1b0-f39f-44ed-9cdf-3bad9a19fc1e.jpg","fdNameEn":"Tourist spots","fdStatus":1,"fdNameCn":"旅游"},{"fdId":"1489220364512W4B53G","fddUrl":"upload/address/6398a1ff-95dc-4c9e-bdd4-bc4c1a6d2121.jpg","fdNameEn":"Shopping","fdStatus":1,"fdNameCn":"购物"}]
     */

    private String code;
    private String message;
    /**
     * fdId : 148093478130504R3YV
     * fddUrl : upload/address/f2b4cf4d-4b55-4da3-a8b5-b959e0e64968.jpg
     * fdNameEn : Restaurant
     * fdStatus : 3
     * fdNameCn : 餐厅
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
        private String fdId;
        private String fddUrl;
        private String fdNameEn;
        private int fdStatus;
        private String fdNameCn;

        public String getFdId() {
            return fdId;
        }

        public void setFdId(String fdId) {
            this.fdId = fdId;
        }

        public String getFddUrl() {
            return fddUrl;
        }

        public void setFddUrl(String fddUrl) {
            this.fddUrl = fddUrl;
        }

        public String getFdNameEn() {
            return fdNameEn;
        }

        public void setFdNameEn(String fdNameEn) {
            this.fdNameEn = fdNameEn;
        }

        public int getFdStatus() {
            return fdStatus;
        }

        public void setFdStatus(int fdStatus) {
            this.fdStatus = fdStatus;
        }

        public String getFdNameCn() {
            return fdNameCn;
        }

        public void setFdNameCn(String fdNameCn) {
            this.fdNameCn = fdNameCn;
        }
    }
}
