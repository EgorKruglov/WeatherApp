package com.example.demo.tables;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tblDefaultTriggers")
public class tblDefaultTriggers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "default_trigger_id")
    private Integer default_trigger_id;
    @OneToOne(mappedBy = "default_triggerID")
    private tblLocations locations;

    @OneToMany(mappedBy = "default_triggerID", fetch = FetchType.EAGER)
    private List<tblDefaultConditions> conditions;

    @Column(name = "celsius_min")
    private Byte celsius_min;

    @Column(name = "celsius_max")
    private Byte celsius_max;

    @Column(name = "humidity_min")
    private Integer humidity_min;

    @Column(name = "humidity_max")
    private Integer humidity_max;

    @Column(name = "wind_speed_min")
    private Double wind_speed_min;

    @Column(name = "wind_speed_max")
    private Double wind_speed_max;

    public Integer getDefault_trigger_id() {
        return default_trigger_id;
    }

    public void setDefault_trigger_id(Integer default_trigger_id) {
        this.default_trigger_id = default_trigger_id;
    }

    public List<tblDefaultConditions> getConditions() {
        return conditions;
    }

    public void setConditions(List<tblDefaultConditions> conditions) {
        this.conditions = conditions;
    }


    public Byte getCelsius_min() {
        return celsius_min;
    }

    public void setCelsius_min(Byte celsius_min) {
        this.celsius_min = celsius_min;
    }

    public Byte getCelsius_max() {
        return celsius_max;
    }

    public void setCelsius_max(Byte celsius_max) {
        this.celsius_max = celsius_max;
    }

    public Integer getHumidity_min() {
        return humidity_min;
    }

    public void setHumidity_min(Integer humidity_min) {
        this.humidity_min = humidity_min;
    }

    public Integer getHumidity_max() {
        return humidity_max;
    }

    public void setHumidity_max(Integer humidity_max) {
        this.humidity_max = humidity_max;
    }

    public Double getWind_speed_min() {
        return wind_speed_min;
    }

    public void setWind_speed_min(Double wind_speed_min) {
        this.wind_speed_min = wind_speed_min;
    }

    public Double getWind_speed_max() {
        return wind_speed_max;
    }

    public void setWind_speed_max(Double wind_speed_max) {
        this.wind_speed_max = wind_speed_max;
    }
}
