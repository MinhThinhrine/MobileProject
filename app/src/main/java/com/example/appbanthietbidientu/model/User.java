package com.example.appbanthietbidientu.model;

import java.io.Serializable;

public class User implements Serializable {
    String email;
    String pass;
    int id;
    String phone;
    String address;
    String role;
    String userName;
    public User() {
    }


    public User(String email, String pass, int id, String phone, String address, String role, String userName) {
        this.email = email;
        this.pass = pass;
        this.id = id;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.userName = userName;
    }

    public User(String email, String pass, int id, String role) {
        this.email = email;
        this.pass = pass;
        this.id = id;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}