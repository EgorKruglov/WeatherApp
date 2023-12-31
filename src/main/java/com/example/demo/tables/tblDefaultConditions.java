package com.example.demo.tables;

import jakarta.persistence.*;

@Entity
@Table(name = "tblDefaultConditions")
public class tblDefaultConditions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "default_condition_id")
    private Integer default_condition_id;

    @Column(name = "condition_", length = 40)
    private String condition;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "default_triggerID")
    private tblDefaultTriggers default_triggerID;


    public void setDefault_triggerID(tblDefaultTriggers default_triggerID) {
        this.default_triggerID=default_triggerID;
    }

    public Integer getDefault_condition_id() {
        return default_condition_id;
    }
    public void setDefault_condition_id(Integer default_condition_id) {this.default_condition_id = default_condition_id;}

    public String getCondition() {
        return condition;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }

}
