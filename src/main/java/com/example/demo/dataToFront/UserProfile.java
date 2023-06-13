package com.example.demo.dataToFront;

import com.example.demo.tables.tblLocations;

import java.util.List;

public class UserProfile {
    private Integer user_id;
    private String name;
    private String surname;
    private Long phone_number;
    private String email;
    private List<LocationToFront> locations;

    public Integer getUser_id() {
        return user_id;
    }
    public void setUser_idId(Integer user_id) {
        this.user_id = user_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {return surname;}
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public Long getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(Long phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {
        this.email = email;
    }

    public List<LocationToFront> getLocations() {return locations;}
    public void setLocations(List<LocationToFront> locations) {
        this.locations = locations;
    }
}
