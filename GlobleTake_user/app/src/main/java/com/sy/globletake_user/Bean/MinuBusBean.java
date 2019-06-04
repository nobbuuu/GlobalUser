package com.sy.globletake_user.Bean;

import java.util.List;

/**
 * Created by sunnetdesign3 on 2017/3/6.
 */
public class MinuBusBean {

    /**
     * code : 1
     * message : 执行成功!
     * result : [{"minibus":[{"fdNumber":"15570086963","fdId":"14871460232683424WI","fdEnName":"zhangsan","fdTitle":"金边---东门","fdVehicleNumber":"123456","fdName":"张三","fdEnTitle":"jinbian---dongmen"}],"fdTitle":"jinbian---dongmen"},{"minibus":[{"fdNumber":"15570086963","fdId":"1487146036799TDBYCP","fdEnName":"lisi","fdTitle":"南山---福田","fdVehicleNumber":"456456","fdName":"李四","fdEnTitle":"nanshan---futian"}],"fdTitle":"nanshan---futian"}]
     */

    private String code;
    private String message;
    /**
     * minibus : [{"fdNumber":"15570086963","fdId":"14871460232683424WI","fdEnName":"zhangsan","fdTitle":"金边---东门","fdVehicleNumber":"123456","fdName":"张三","fdEnTitle":"jinbian---dongmen"}]
     * fdTitle : jinbian---dongmen
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
        private String fdTitle;
        /**
         * fdNumber : 15570086963
         * fdId : 14871460232683424WI
         * fdEnName : zhangsan
         * fdTitle : 金边---东门
         * fdVehicleNumber : 123456
         * fdName : 张三
         * fdEnTitle : jinbian---dongmen
         */

        private List<MinibusBean> minibus;

        public String getFdTitle() {
            return fdTitle;
        }

        public void setFdTitle(String fdTitle) {
            this.fdTitle = fdTitle;
        }

        public List<MinibusBean> getMinibus() {
            return minibus;
        }

        public void setMinibus(List<MinibusBean> minibus) {
            this.minibus = minibus;
        }

        public static class MinibusBean {
            private String fdNumber;
            private String fdId;
            private String fdEnName;
            private String fdTitle;
            private String fdVehicleNumber;
            private String fdName;
            private String fdEnTitle;

            public String getFdNumber() {
                return fdNumber;
            }

            public void setFdNumber(String fdNumber) {
                this.fdNumber = fdNumber;
            }

            public String getFdId() {
                return fdId;
            }

            public void setFdId(String fdId) {
                this.fdId = fdId;
            }

            public String getFdEnName() {
                return fdEnName;
            }

            public void setFdEnName(String fdEnName) {
                this.fdEnName = fdEnName;
            }

            public String getFdTitle() {
                return fdTitle;
            }

            public void setFdTitle(String fdTitle) {
                this.fdTitle = fdTitle;
            }

            public String getFdVehicleNumber() {
                return fdVehicleNumber;
            }

            public void setFdVehicleNumber(String fdVehicleNumber) {
                this.fdVehicleNumber = fdVehicleNumber;
            }

            public String getFdName() {
                return fdName;
            }

            public void setFdName(String fdName) {
                this.fdName = fdName;
            }

            public String getFdEnTitle() {
                return fdEnTitle;
            }

            public void setFdEnTitle(String fdEnTitle) {
                this.fdEnTitle = fdEnTitle;
            }
        }
    }
}
