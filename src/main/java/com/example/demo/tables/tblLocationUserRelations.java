package com.example.demo.tables;

import jakarta.persistence.*;

@Entity
@Table(name = "tblLocationUserRelations")
public class tblLocationUserRelations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_user_relation_id")
    private Integer location_user_relation_id;

    @ManyToOne
    @JoinColumn(name = "locationID")
    private tblLocations locations;

    @ManyToOne
    @JoinColumn(name = "userID")
    private tblUsers userId;


    public Integer getLocation_user_relation_id() {
        return location_user_relation_id;
    }

    public void setLocation_user_relation_id(Integer location_user_relation_id) {
        this.location_user_relation_id = location_user_relation_id;
    }

    public tblLocations getLocations() {
        return locations;
    }

    public void setLocations(tblLocations locations) {
        this.locations = locations;
    }

    public tblUsers getUserId() {
        return userId;
    }

    public void setUserId(tblUsers userId) {
        this.userId = userId;
    }
}