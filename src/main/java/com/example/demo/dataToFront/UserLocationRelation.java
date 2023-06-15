package com.example.demo.dataToFront;

import java.util.ArrayList;
import java.util.List;

public class UserLocationRelation {
    private List<Node> relations = new ArrayList<>();

    public void addRelation(Integer user_id, String location_name) {
        relations.add(new Node(user_id, location_name));
    }

    public List<Node> getRelations() {
        return relations;
    }

    public void setRelations(List<Node> relations) {
        this.relations = relations;
    }
}

class Node {
    private Integer user_id;
    private String location_name;

    public Node(Integer user_id, String location_name) {
        this.user_id = user_id;
        this.location_name = location_name;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public String getlocation_name() {
        return location_name;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setlocation_name(String location_name) {
        this.location_name = location_name;
    }
}
