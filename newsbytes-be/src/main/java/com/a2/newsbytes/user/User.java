package com.a2.newsbytes.user;

import jakarta.persistence.*;


@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uname;
    private String password;
    private String roles;
    private String status;

    public User(){}
    public User(User user){
        this.id=user.id;
        this.uname=user.uname;
        this.password=user.password;
        this.roles=user.roles;
        this.status=user.status;
    }
    public User(String uname, String password, String roles, String status) {
        this.uname = uname;
        this.password = password;
        this.roles = roles;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}