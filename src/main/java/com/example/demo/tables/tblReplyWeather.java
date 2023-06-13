package com.example.demo.tables;


import jakarta.persistence.*;
import org.hibernate.type.descriptor.jdbc.TinyIntJdbcType;

@Entity
@Table(name = "tblReplyWeather")
public class tblReplyWeather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_trigger_id")
    private Integer reply_trigger_id;
    @OneToOne(mappedBy = "reply_weatherID")
    private tblAutoRequestHistory weather;

    @Column(name = "celsius")
    private Byte celsius;

    @Column(name = "humidity")
    private Integer humidity;

    @Column(name = "condition_", length = 40)
    private String condition;

    @Column(name = "wind_speed")
    private Double wind_speed;


    public Integer getReply_trigger_id() {
        return reply_trigger_id;
    }
    public void setReply_trigger_id(Integer reply_trigger_id) {
        this.reply_trigger_id = reply_trigger_id;
    }

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
