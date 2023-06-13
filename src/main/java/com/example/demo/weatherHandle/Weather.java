package com.example.demo.weatherHandle;

public class Weather {
    private String date;
    private Byte celsius;
    private Integer humidity;
    private String condition;
    private Double wind_speed;

    public Byte getCelsius() {
        return celsius;
    }

    public void setCelsius(Byte celsius) {
        this.celsius = celsius;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(Double wind_speed) {
        this.wind_speed = wind_speed;
    }
}
