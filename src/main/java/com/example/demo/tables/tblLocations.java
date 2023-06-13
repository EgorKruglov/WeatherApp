package com.example.demo.tables;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "tblLocations")
public class tblLocations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Integer location_id;
    @OneToMany(mappedBy = "locations")
    private List<tblLocationUserRelations> relations;
    @OneToMany(mappedBy = "locationWeather")
    private List<tblAutoRequestHistory> locationWeather;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @Column(name = "location_name", length = 200)
    private String location_name;

    @OneToOne
    @JoinColumn(name = "default_triggerID")
    private tblDefaultTriggers default_triggerID;

    @OneToOne
    @JoinColumn(name = "custom_triggerID")
    private tblCustomTriggers custom_triggerID;


    public Integer getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Integer location_id) {
        this.location_id = location_id;
    }

    public tblDefaultTriggers getDefault_triggerID() {
        return default_triggerID;
    }

    public void setDefault_triggerID(tblDefaultTriggers default_triggerID) {
        this.default_triggerID = default_triggerID;
    }

    public tblCustomTriggers getCustom_triggerID() {
        return custom_triggerID;
    }

    public void setCustom_triggerID(tblCustomTriggers custom_triggerID) {
        this.custom_triggerID = custom_triggerID;
    }


    public Double getLat() {
        return lat;
    }

    public void setLocation_id(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

}
