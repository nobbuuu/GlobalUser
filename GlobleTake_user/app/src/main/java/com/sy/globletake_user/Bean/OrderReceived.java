package com.sy.globletake_user.Bean;

/**
 * Created by sunnetdesign3 on 2017/2/22.
 */
public class OrderReceived {

    /**
     * order_id : 1487767961409EBBGBM
     * tag : 1
     */

    private String order_id;
    private String tag;
    private String EventStr;

    public String getEventStr() {
        return EventStr;
    }

    public void setEventStr(String eventStr) {
        EventStr = eventStr;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
