package com.example.demo.tables;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "tblUsers")
public class tblUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer user_id;
    @OneToOne(mappedBy = "adminUsers")
    private tblAdminCodes admin;
    @OneToMany(mappedBy = "userId")
    private List<tblLocationUserRelations> relations;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "surname", length = 40)
    private String surname;

    @Column(name = "phone_number")
    private Long phone_number;

    @Column(name = "mail", length = 100)
    private String mail;

    @Column(name = "password", length = 30)
    private String password;

    @Column(name = "root")
    private Byte root;

    @Column(name = "registr_date")
    @Temporal(TemporalType.DATE)
    private Date registr_date;


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


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(Long phone_number) {
        this.phone_number = phone_number;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte getRoot() {
        return root;
    }

    public void setRoot(Byte root) {
        this.root = root;
    }

    public Date getRegistr_date() {
        return registr_date;
    }

    public void setRegistr_date(Date registr_date) {
        this.registr_date = registr_date;
    }
}
