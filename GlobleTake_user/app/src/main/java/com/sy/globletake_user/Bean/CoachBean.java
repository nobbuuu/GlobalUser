package com.sy.globletake_user.Bean;

import java.util.List;

/**
 * Created by sunnetdesign3 on 2017/3/6.
 */
public class CoachBean  {

    /**
     * code : 1
     * message : 执行成功!
     * result : [{"date":[{"information":{"fdEnContent":"09:00AM  10:30AM  11:00AM  11:30AM  12:40AM","fdCarNumber":"4444","fdContent":"09:00AM  10:30AM  11:00AM  11:30AM  12:40AM","fdId":"1487232525523X99T00","fdEnOperatingday":"homeworkday","fdOperatingday":"工作日","fdTitle":"哈哈哈","fdCreateTime":1487232525491,"fdEnTitle":"hhhh"},"imformationType":{"fdCode":"1","fdEnEndName":"121","fdId":"1484191617206GJTXPN","fdStatus":1,"fdEndName":"1023","fdEnName":"not","fdCreateTime":1484191617186,"fdName":"物流货运"}}],"fdPhone":"13424319818","fdName":"SIX"},{"date":[{"information":{"fdEnContent":"09:00AM  10:30AM  11:00AM  11:30AM  12:40AM","fdCarNumber":"123456","fdContent":"09:00AM  10:30AM  11:00AM  11:30AM  12:40AM","fdId":"1486635234633LYL2SH","fdEnOperatingday":"today","fdOperatingday":"每天","fdTitle":"123","fdCreateTime":1486635234633,"fdEnTitle":"sss"},"imformationType":{"fdCode":"1","fdEnEndName":"121","fdId":"1484191617206GJTXPN","fdStatus":1,"fdEndName":"1023","fdEnName":"not","fdCreateTime":1484191617186,"fdName":"物流货运"}},{"information":{"fdEnContent":"09:00AM  10:00 AM  11:00AM","fdCarNumber":"121211","fdContent":"09:00AM  10:00 AM  11:00AM","fdId":"1487323325473HQCNUH","fdEnOperatingday":"today","fdOperatingday":"每天","fdTitle":"深圳大巴","fdCreateTime":1487323325449,"fdEnTitle":"shenzhendaba"},"imformationType":{"fdCode":"1","fdEnEndName":"121","fdId":"1484191617206GJTXPN","fdStatus":1,"fdEndName":"1023","fdEnName":"not","fdCreateTime":1484191617186,"fdName":"物流货运"}}],"fdPhone":"15896369654","fdName":"hhhh"}]
     */

    private String code;
    private String message;
    /**
     * date : [{"information":{"fdEnContent":"09:00AM  10:30AM  11:00AM  11:30AM  12:40AM","fdCarNumber":"4444","fdContent":"09:00AM  10:30AM  11:00AM  11:30AM  12:40AM","fdId":"1487232525523X99T00","fdEnOperatingday":"homeworkday","fdOperatingday":"工作日","fdTitle":"哈哈哈","fdCreateTime":1487232525491,"fdEnTitle":"hhhh"},"imformationType":{"fdCode":"1","fdEnEndName":"121","fdId":"1484191617206GJTXPN","fdStatus":1,"fdEndName":"1023","fdEnName":"not","fdCreateTime":1484191617186,"fdName":"物流货运"}}]
     * fdPhone : 13424319818
     * fdName : SIX
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
        private String fdPhone;
        private String fdName;
        /**
         * information : {"fdEnContent":"09:00AM  10:30AM  11:00AM  11:30AM  12:40AM","fdCarNumber":"4444","fdContent":"09:00AM  10:30AM  11:00AM  11:30AM  12:40AM","fdId":"1487232525523X99T00","fdEnOperatingday":"homeworkday","fdOperatingday":"工作日","fdTitle":"哈哈哈","fdCreateTime":1487232525491,"fdEnTitle":"hhhh"}
         * imformationType : {"fdCode":"1","fdEnEndName":"121","fdId":"1484191617206GJTXPN","fdStatus":1,"fdEndName":"1023","fdEnName":"not","fdCreateTime":1484191617186,"fdName":"物流货运"}
         */

        private List<DateBean> date;

        public String getFdPhone() {
            return fdPhone;
        }

        public void setFdPhone(String fdPhone) {
            this.fdPhone = fdPhone;
        }

        public String getFdName() {
            return fdName;
        }

        public void setFdName(String fdName) {
            this.fdName = fdName;
        }

        public List<DateBean> getDate() {
            return date;
        }

        public void setDate(List<DateBean> date) {
            this.date = date;
        }

        public static class DateBean {
            /**
             * fdEnContent : 09:00AM  10:30AM  11:00AM  11:30AM  12:40AM
             * fdCarNumber : 4444
             * fdContent : 09:00AM  10:30AM  11:00AM  11:30AM  12:40AM
             * fdId : 1487232525523X99T00
             * fdEnOperatingday : homeworkday
             * fdOperatingday : 工作日
             * fdTitle : 哈哈哈
             * fdCreateTime : 1487232525491
             * fdEnTitle : hhhh
             */

            private InformationBean information;
            /**
             * fdCode : 1
             * fdEnEndName : 121
             * fdId : 1484191617206GJTXPN
             * fdStatus : 1
             * fdEndName : 1023
             * fdEnName : not
             * fdCreateTime : 1484191617186
             * fdName : 物流货运
             */

            private ImformationTypeBean imformationType;

            public InformationBean getInformation() {
                return information;
            }

            public void setInformation(InformationBean information) {
                this.information = information;
            }

            public ImformationTypeBean getImformationType() {
                return imformationType;
            }

            public void setImformationType(ImformationTypeBean imformationType) {
                this.imformationType = imformationType;
            }

            public static class InformationBean {
                private String fdEnContent;
                private String fdCarNumber;
                private String fdContent;
                private String fdId;
                private String fdEnOperatingday;
                private String fdOperatingday;
                private String fdTitle;
                private long fdCreateTime;
                private String fdEnTitle;

                public String getFdEnContent() {
                    return fdEnContent;
                }

                public void setFdEnContent(String fdEnContent) {
                    this.fdEnContent = fdEnContent;
                }

                public String getFdCarNumber() {
                    return fdCarNumber;
                }

                public void setFdCarNumber(String fdCarNumber) {
                    this.fdCarNumber = fdCarNumber;
                }

                public String getFdContent() {
                    return fdContent;
                }

                public void setFdContent(String fdContent) {
                    this.fdContent = fdContent;
                }

                public String getFdId() {
                    return fdId;
                }

                public void setFdId(String fdId) {
                    this.fdId = fdId;
                }

                public String getFdEnOperatingday() {
                    return fdEnOperatingday;
                }

                public void setFdEnOperatingday(String fdEnOperatingday) {
                    this.fdEnOperatingday = fdEnOperatingday;
                }

                public String getFdOperatingday() {
                    return fdOperatingday;
                }

                public void setFdOperatingday(String fdOperatingday) {
                    this.fdOperatingday = fdOperatingday;
                }

                public String getFdTitle() {
                    return fdTitle;
                }

                public void setFdTitle(String fdTitle) {
                    this.fdTitle = fdTitle;
                }

                public long getFdCreateTime() {
                    return fdCreateTime;
                }

                public void setFdCreateTime(long fdCreateTime) {
                    this.fdCreateTime = fdCreateTime;
                }

                public String getFdEnTitle() {
                    return fdEnTitle;
                }

                public void setFdEnTitle(String fdEnTitle) {
                    this.fdEnTitle = fdEnTitle;
                }
            }

            public static class ImformationTypeBean {
                private String fdCode;
                private String fdEnEndName;
                private String fdId;
                private String fdStatus;
                private String fdEndName;
                private String fdEnName;
                private long fdCreateTime;
                private String fdName;

                public String getFdCode() {
                    return fdCode;
                }

                public void setFdCode(String fdCode) {
                    this.fdCode = fdCode;
                }

                public String getFdEnEndName() {
                    return fdEnEndName;
                }

                public void setFdEnEndName(String fdEnEndName) {
                    this.fdEnEndName = fdEnEndName;
                }

                public String getFdId() {
                    return fdId;
                }

                public void setFdId(String fdId) {
                    this.fdId = fdId;
                }

                public String getFdStatus() {
                    return fdStatus;
                }

                public void setFdStatus(String fdStatus) {
                    this.fdStatus = fdStatus;
                }

                public String getFdEndName() {
                    return fdEndName;
                }

                public void setFdEndName(String fdEndName) {
                    this.fdEndName = fdEndName;
                }

                public String getFdEnName() {
                    return fdEnName;
                }

                public void setFdEnName(String fdEnName) {
                    this.fdEnName = fdEnName;
                }

                public long getFdCreateTime() {
                    return fdCreateTime;
                }

                public void setFdCreateTime(long fdCreateTime) {
                    this.fdCreateTime = fdCreateTime;
                }

                public String getFdName() {
                    return fdName;
                }

                public void setFdName(String fdName) {
                    this.fdName = fdName;
                }
            }
        }
    }
}
