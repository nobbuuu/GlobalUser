package com.sy.globletake_user.Bean;

/**
 * Created by sunnetdesign3 on 2017/2/24.
 */
public class PriceRuleBean {

    /**
     * code : 1
     * message : 执行成功!
     * result : {"min":150,"journey":1100,"moneybymin":450,"moneybytime":230,"startingfare":3500}
     * {"code":"1","message":"执行成功!","result":{"min":1000,"journey":1000,"moneybymin":2000,"startingfare":2000}}
     */

    private String code;
    private String message;
    /**
     * min : 150
     * journey : 1100
     * moneybymin : 450
     * moneybytime : 230
     * startingfare : 3500
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
        private int min;
        private int journey;
        private int moneybymin;
        private int moneybytime;
        private int startingfare;

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getJourney() {
            return journey;
        }

        public void setJourney(int journey) {
            this.journey = journey;
        }

        public int getMoneybymin() {
            return moneybymin;
        }

        public void setMoneybymin(int moneybymin) {
            this.moneybymin = moneybymin;
        }

        public int getMoneybytime() {
            return moneybytime;
        }

        public void setMoneybytime(int moneybytime) {
            this.moneybytime = moneybytime;
        }

        public int getStartingfare() {
            return startingfare;
        }

        public void setStartingfare(int startingfare) {
            this.startingfare = startingfare;
        }
    }
}
