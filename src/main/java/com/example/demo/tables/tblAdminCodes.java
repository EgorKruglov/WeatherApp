package com.example.demo.tables;

import jakarta.persistence.*;

@Entity
@Table(name = "tblAdminCodes")
public class tblAdminCodes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_code_id")
    private Integer admin_code_id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adminID")
    private tblUsers adminUsers;

    @Column(name = "code", length = 45)
    private String code;


    public Integer getAdmin_code_id() {
        return admin_code_id;
    }

    public void setAdmin_code_id(Integer admin_code_id) {
        this.admin_code_id = admin_code_id;
    }

    public tblUsers getAdminUsers() {
        return adminUsers;
    }

    public void setAdminUsers(tblUsers adminUsers) {
        this.adminUsers = adminUsers;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
