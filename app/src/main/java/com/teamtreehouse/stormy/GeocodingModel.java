package com.teamtreehouse.stormy;

public class GeocodingModel {



        //LAT LON
        private float lat;
        private float lon;

        //Weather in CITY, COUNTRY
        private String name;
        private String country;

        //Weather (main and description)
        private String main;
        private String description;

        //temp
        private float temp;

        //Temperature (min)
        private float temp_min;

        //Temperature (max)
        private float temp_max;

        public GeocodingModel() {

        }

        //used for data binding
        public GeocodingModel(float lat, float lon, String name, String country, String main, String description, float temp, float temp_min, float temp_max) {
            this.lat=lat;
            this.lon=lon;
            this.name = name;
            this.country = country;
            this.main = main;
            this.description = description;
            this.temp = temp;
            this.temp_min = temp_min;
            this.temp_max = temp_max;
        }



        public float getLat() {
            return lat;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }

        public float getLon() {
            return lon;
        }

        public void setLon(float lon) {
            this.lon = lon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public float getTemp() {
            return temp;
        }

        public void setTemp(float temp) {
            this.temp = temp;
        }

        public float getTemp_min() {
            return temp_min;
        }

        public void setTemp_min(float temp_min) {
            this.temp_min = temp_min;
        }

        public float getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(float temp_max) {
            this.temp_max = temp_max;
        }

    }


