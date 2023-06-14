package com.example.demo.tables;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tblCustomConditions")
public class tblCustomConditions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_trigger_id")
    private Integer custom_trigger_id;

    @Column(name = "condition_", length = 40)
    private String condition;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "custom_triggerID")
    private tblCustomTriggers custom_triggerID;


    public void setConditions(tblCustomTriggers custom_triggerID) {
        this.custom_triggerID = custom_triggerID;
    }

    public Integer getCustom_trigger_id() {
        return custom_trigger_id;
    }
    public void setCustom_trigger_id(Integer custom_trigger_id) {
        this.custom_trigger_id = custom_trigger_id;
    }

    public String getCondition() {
        return condition;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }


}
