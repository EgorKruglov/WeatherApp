package com.example.demo.dataToFront;

import com.example.demo.dataToFront.Weather;

import java.util.List;

public class WeatherToFront {
    private String location;
    private Double lat;
    private Double lon;
    private List<Weather> weather;

    public Double getLat() {
        return lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }
    public void setLon(Double lon) {
        this.lon = lon;
    }
    public List<Weather> getWeather() {
        return weather;
    }
    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getLocation() {return location;}
    public void setLocation(String location) {
        this.location = location;
    }
}
