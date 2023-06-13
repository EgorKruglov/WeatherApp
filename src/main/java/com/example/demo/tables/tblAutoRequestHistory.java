package com.example.demo.tables;


import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "tblAutoRequestHistory")
public class tblAutoRequestHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auto_history_id")
    private Integer auto_history_id;

    @ManyToOne
    @JoinColumn(name = "locationID")
    private tblLocations locationWeather;

    @Column(name = "resp_time")
    private Timestamp resp_time;

    @OneToOne
    @JoinColumn(name = "reply_weatherID")
    private tblReplyWeather reply_weatherID;

    @Column(name = "reply_time")
    private Timestamp reply_time;


    public Integer getAuto_history_id() {
        return auto_history_id;
    }

    public void setAuto_history_id(Integer auto_history_id) {
        this.auto_history_id = auto_history_id;
    }

    public tblReplyWeather getReply_weatherID() {
        return reply_weatherID;
    }

    public void setReply_weatherID(tblReplyWeather reply_weatherID) {
        this.reply_weatherID = reply_weatherID;
    }

    public tblLocations getLocationWeather() {
        return locationWeather;
    }

    public void setLocationWeather(tblLocations locationWeather) {
        this.locationWeather = locationWeather;
    }

    public Timestamp getResp_time() {
        return resp_time;
    }

    public void setResp_time(Timestamp resp_time) {
        this.resp_time = resp_time;
    }


    public Timestamp getReply_time() {
        return reply_time;
    }

    public void setReply_time(Timestamp reply_time) {
        this.reply_time = reply_time;
    }
}
