package com.sy.globletake_user.utils;

import com.sy.globletake_user.Bean.LatlngBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunnetdesign3 on 2017/3/13.
 */
public class WGSTOGCJ02 {

    private static double pi = 3.14159265358979324D;// 圆周率

    private static double a = 6378245.0D;// WGS 长轴半径

    private static double ee = 0.00669342162296594323D;// WGS 偏心率的平方

    /**

     * 中国坐标内

     *

     * @param lat

     * @param lon

     * @return

     */

    public static boolean outofChina(double lat, double lon) {

        if (lon < 72.004 || lon > 137.8347)

            return true;

        if (lat < 0.8293 || lat > 55.8271)

            return true;

        return false;

    }

    public static double transformLat(double x, double y) {

        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y

                + 0.2 * Math.sqrt(Math.abs(x));

        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;

        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;

        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;

        return ret;

    }

// 84->gcj02

    public static double transformLon(double x, double y) {

        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1

                * Math.sqrt(Math.abs(x));

        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;

        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;

        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0

                * pi)) * 2.0 / 3.0;

        return ret;

    }

    public static LatlngBean transform(double lon, double lat) {

        LatlngBean latlngBean = new LatlngBean();

        if (outofChina(lat, lon)) {

            latlngBean.setLon(Double.valueOf(lon));

            latlngBean.setLat(Double.valueOf(lat));
            return latlngBean;

        }

        double dLat = transformLat(lon - 105.0, lat - 35.0);

        double dLon = transformLon(lon - 105.0, lat - 35.0);

        double radLat = lat / 180.0 * pi;

        double magic = Math.sin(radLat);

        magic = 1 - ee * magic * magic;

        double sqrtMagic = Math.sqrt(magic);

        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);

        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);

        double mgLat = lat + dLat;

        double mgLon = lon + dLon;

        latlngBean.setLon(mgLon);

        latlngBean.setLat(mgLat);
        return latlngBean;

    }

// gcj02-84

    public LatlngBean gcj2wgs(double lon, double lat) {

        LatlngBean localHashMap = new LatlngBean();

        double lontitude = lon

                - (((Double) transform(lon, lat).getLon()).doubleValue() - lon);

        double latitude = (lat - (((Double) ( transform(lon, lat))

                .getLat()).doubleValue() - lat));

        localHashMap.setLon(lontitude);
        localHashMap.setLat(latitude);
        return localHashMap;

    }

}
