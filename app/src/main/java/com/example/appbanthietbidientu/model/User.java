package com.example.appbanthietbidientu.model;

public class User {
    String email;
    String pass;
    int id;

    public User(String email, String pass, int id) {
        this.email = email;
        this.pass = pass;
        this.id = id;
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
}
