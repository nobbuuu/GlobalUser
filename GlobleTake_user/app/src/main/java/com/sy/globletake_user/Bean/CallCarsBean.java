package com.sy.globletake_user.Bean;

/**
 * Created by sunnetdesign3 on 2017/2/17.
 */
public class CallCarsBean {

    /**
     * code : 1
     * message : 执行成功!
     * result : {"fdTripCreateTime":1487336681029,"fdTripStartCoordinates":113.915125,"passenger_fdPhone":"18277750576","fdVehicleTypeNum":"","fdStatus":"","fdTripNum":"201702172104415497","fdTripEndCoordinateslongitude":22.535006648941,"fdTripStartLocation":"深圳市南山大厦-北座","fdTripStartTime":0,"fdTripEndLocation":"世界之窗","fdTripPrice":0,"fdId":"1487336681029Y80VRA","fdTripEndCoordinates":113.97465188433,"passenger_fdIconUrl":"upload/userImg/fffbcc37-559c-4f3d-83ae-9efec6973917.jpg","fdTripMileage":0,"fdTripEndTime":0,"fdName":"","fdTripPeople":1,"fdTripStartCoordinateslongitude":22.499011}
     */

    private String code;
    private String message;
    /**
     * fdTripCreateTime : 1487336681029
     * fdTripStartCoordinates : 113.915125
     * passenger_fdPhone : 18277750576
     * fdVehicleTypeNum :
     * fdStatus :
     * fdTripNum : 201702172104415497
     * fdTripEndCoordinateslongitude : 22.535006648941
     * fdTripStartLocation : 深圳市南山大厦-北座
     * fdTripStartTime : 0
     * fdTripEndLocation : 世界之窗
     * fdTripPrice : 0
     * fdId : 1487336681029Y80VRA
     * fdTripEndCoordinates : 113.97465188433
     * passenger_fdIconUrl : upload/userImg/fffbcc37-559c-4f3d-83ae-9efec6973917.jpg
     * fdTripMileage : 0
     * fdTripEndTime : 0
     * fdName :
     * fdTripPeople : 1
     * fdTripStartCoordinateslongitude : 22.499011
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
        private long fdTripCreateTime;
        private double fdTripStartCoordinates;
        private String passenger_fdPhone;
        private String fdVehicleTypeNum;
        private String fdStatus;
        private String fdTripNum;
        private double fdTripEndCoordinateslongitude;
        private String fdTripStartLocation;
        private int fdTripStartTime;
        private String fdTripEndLocation;
        private int fdTripPrice;
        private String fdId;
        private double fdTripEndCoordinates;
        private String passenger_fdIconUrl;
        private int fdTripMileage;
        private int fdTripEndTime;
        private String fdName;
        private int fdTripPeople;
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

        public int getFdTripStartTime() {
            return fdTripStartTime;
        }

        public void setFdTripStartTime(int fdTripStartTime) {
            this.fdTripStartTime = fdTripStartTime;
        }

        public String getFdTripEndLocation() {
            return fdTripEndLocation;
        }

        public void setFdTripEndLocation(String fdTripEndLocation) {
            this.fdTripEndLocation = fdTripEndLocation;
        }

        public int getFdTripPrice() {
            return fdTripPrice;
        }

        public void setFdTripPrice(int fdTripPrice) {
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

        public int getFdTripMileage() {
            return fdTripMileage;
        }

        public void setFdTripMileage(int fdTripMileage) {
            this.fdTripMileage = fdTripMileage;
        }

        public int getFdTripEndTime() {
            return fdTripEndTime;
        }

        public void setFdTripEndTime(int fdTripEndTime) {
            this.fdTripEndTime = fdTripEndTime;
        }

        public String getFdName() {
            return fdName;
        }

        public void setFdName(String fdName) {
            this.fdName = fdName;
        }

        public int getFdTripPeople() {
            return fdTripPeople;
        }

        public void setFdTripPeople(int fdTripPeople) {
            this.fdTripPeople = fdTripPeople;
        }

        public double getFdTripStartCoordinateslongitude() {
            return fdTripStartCoordinateslongitude;
        }

        public void setFdTripStartCoordinateslongitude(double fdTripStartCoordinateslongitude) {
            this.fdTripStartCoordinateslongitude = fdTripStartCoordinateslongitude;
        }
    }
}
