package com.teamtreehouse.stormy;

public class LocationModel {

    private String populatedPlace;
    private String countyUnitary;
    private String region;
    private String country;

    public String getCountyUnitary() {
        return countyUnitary;
    }

    public void setCountyUnitary(String countyUnitary) {
        this.countyUnitary = countyUnitary;
    }

    public String getPopulatedPlace() {
        return populatedPlace;
    }

    public void setPopulatedPlace(String populatedPlace) {
        this.populatedPlace = populatedPlace;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocationModel() {

    }

    /*  "header": {
        "uri": "https://api.os.uk/search/names/v1/find?query=so16%200as",
                "query": "so16 0as",
                "format": "JSON",
                "maxresults": 100,
                "offset": 0,
                "totalresults": 2481
    },
            "results": [
    {
        "GAZETTEER_ENTRY": {
        "ID": "SO160AS",
                "NAMES_URI": "http://data.ordnancesurvey.co.uk/id/postcodeunit/SO160AS",
                "NAME1": "SO16 0AS",
                "TYPE": "other",
                "LOCAL_TYPE": "Postcode",
                "GEOMETRY_X": 437292.0,
                "GEOMETRY_Y": 115542.0,
                "MOST_DETAIL_VIEW_RES": 3500,
                "LEAST_DETAIL_VIEW_RES": 18000,
                "POPULATED_PLACE": "Nursling",
                "POPULATED_PLACE_URI": "http://data.ordnancesurvey.co.uk/id/4000000074566646",
                "POPULATED_PLACE_TYPE": "http://www.ordnancesurvey.co.uk/xml/codelists/localtype.xml#village",
                "DISTRICT_BOROUGH": "Test Valley",
                "DISTRICT_BOROUGH_URI": "http://data.ordnancesurvey.co.uk/id/7000000000043511",
                "DISTRICT_BOROUGH_TYPE": "http://data.ordnancesurvey.co.uk/ontology/admingeo/District",
                "COUNTY_UNITARY": "Hampshire",
                "COUNTY_UNITARY_URI": "http://data.ordnancesurvey.co.uk/id/7000000000017765",
                "COUNTY_UNITARY_TYPE": "http://data.ordnancesurvey.co.uk/ontology/admingeo/County",
                "REGION": "South East",
                "REGION_URI": "http://data.ordnancesurvey.co.uk/id/7000000000041421",
                "COUNTRY": "England",
                "COUNTRY_URI": "http://data.ordnancesurvey.co.uk/id/country/england"
    }
    },*/
}
