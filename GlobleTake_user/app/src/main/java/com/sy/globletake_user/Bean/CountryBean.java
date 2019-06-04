package com.sy.globletake_user.Bean;

import java.util.List;

/**
 * Created by sunnetdesign3 on 2017/3/15.
 */
public class CountryBean {


    /**
     * results : [{"address_components":[{"long_name":"69BT1","short_name":"69BT1","types":["street_number"]},{"long_name":"69 Road","short_name":"69 Rd","types":["route"]},{"long_name":"Khan Mean Chey","short_name":"Khan Mean Chey","types":["political","sublocality","sublocality_level_1"]},{"long_name":"Phnom Penh","short_name":"PP","types":["locality","political"]},{"long_name":"Meanchey","short_name":"Meanchey","types":["administrative_area_level_2","political"]},{"long_name":"Phnom Penh","short_name":"Phnom Penh","types":["administrative_area_level_1","political"]},{"long_name":"柬埔寨","short_name":"KH","types":["country","political"]}],"formatted_address":"69BT1 69 Rd, Phnom Penh, 柬埔寨","geometry":{"location":{"lat":11.5208772,"lng":104.9140048},"location_type":"ROOFTOP","viewport":{"northeast":{"lat":11.5222261802915,"lng":104.9153537802915},"southwest":{"lat":11.5195282197085,"lng":104.9126558197085}}},"place_id":"ChIJvfoQOJVQCTERAYaZoaWe8D0","types":["street_address"]}]
     * status : OK
     */

    private String status;
    private List<ResultsBean> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * address_components : [{"long_name":"69BT1","short_name":"69BT1","types":["street_number"]},{"long_name":"69 Road","short_name":"69 Rd","types":["route"]},{"long_name":"Khan Mean Chey","short_name":"Khan Mean Chey","types":["political","sublocality","sublocality_level_1"]},{"long_name":"Phnom Penh","short_name":"PP","types":["locality","political"]},{"long_name":"Meanchey","short_name":"Meanchey","types":["administrative_area_level_2","political"]},{"long_name":"Phnom Penh","short_name":"Phnom Penh","types":["administrative_area_level_1","political"]},{"long_name":"柬埔寨","short_name":"KH","types":["country","political"]}]
         * formatted_address : 69BT1 69 Rd, Phnom Penh, 柬埔寨
         * geometry : {"location":{"lat":11.5208772,"lng":104.9140048},"location_type":"ROOFTOP","viewport":{"northeast":{"lat":11.5222261802915,"lng":104.9153537802915},"southwest":{"lat":11.5195282197085,"lng":104.9126558197085}}}
         * place_id : ChIJvfoQOJVQCTERAYaZoaWe8D0
         * types : ["street_address"]
         */

        private String formatted_address;
        private GeometryBean geometry;
        private String place_id;
        private List<AddressComponentsBean> address_components;
        private List<String> types;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public GeometryBean getGeometry() {
            return geometry;
        }

        public void setGeometry(GeometryBean geometry) {
            this.geometry = geometry;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public List<AddressComponentsBean> getAddress_components() {
            return address_components;
        }

        public void setAddress_components(List<AddressComponentsBean> address_components) {
            this.address_components = address_components;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public static class GeometryBean {
            /**
             * location : {"lat":11.5208772,"lng":104.9140048}
             * location_type : ROOFTOP
             * viewport : {"northeast":{"lat":11.5222261802915,"lng":104.9153537802915},"southwest":{"lat":11.5195282197085,"lng":104.9126558197085}}
             */

            private LocationBean location;
            private String location_type;
            private ViewportBean viewport;

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public String getLocation_type() {
                return location_type;
            }

            public void setLocation_type(String location_type) {
                this.location_type = location_type;
            }

            public ViewportBean getViewport() {
                return viewport;
            }

            public void setViewport(ViewportBean viewport) {
                this.viewport = viewport;
            }

            public static class LocationBean {
                /**
                 * lat : 11.5208772
                 * lng : 104.9140048
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            public static class ViewportBean {
                /**
                 * northeast : {"lat":11.5222261802915,"lng":104.9153537802915}
                 * southwest : {"lat":11.5195282197085,"lng":104.9126558197085}
                 */

                private NortheastBean northeast;
                private SouthwestBean southwest;

                public NortheastBean getNortheast() {
                    return northeast;
                }

                public void setNortheast(NortheastBean northeast) {
                    this.northeast = northeast;
                }

                public SouthwestBean getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(SouthwestBean southwest) {
                    this.southwest = southwest;
                }

                public static class NortheastBean {
                    /**
                     * lat : 11.5222261802915
                     * lng : 104.9153537802915
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                public static class SouthwestBean {
                    /**
                     * lat : 11.5195282197085
                     * lng : 104.9126558197085
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }
        }

        public static class AddressComponentsBean {
            /**
             * long_name : 69BT1
             * short_name : 69BT1
             * types : ["street_number"]
             */

            private String long_name;
            private String short_name;
            private List<String> types;

            public String getLong_name() {
                return long_name;
            }

            public void setLong_name(String long_name) {
                this.long_name = long_name;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public List<String> getTypes() {
                return types;
            }

            public void setTypes(List<String> types) {
                this.types = types;
            }
        }
    }
}
