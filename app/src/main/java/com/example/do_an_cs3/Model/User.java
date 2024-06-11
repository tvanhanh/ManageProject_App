package com.example.do_an_cs3.Model;

import android.content.Intent;

public class User {
    private String address;
    private String avatar;
    private String email;
    private String password;
    private String phone;
    private String role;
    private String userName;
    private String deparment;

    public User(String address, String avatar, String email, String password, String phone, String role, String username) {
        this.address = address;
        this.avatar = avatar;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.userName = username;
//        this.deparment = deparment;
    }


    public User(String ha) {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeparment() {
        return deparment;
    }

    public void setDeparment(String deparment) {
        this.deparment = deparment;
    }

    public int getTotalProjects() {
        return totalProjects;
    }


    public User(String userName, String avatar) {
        this.userName = userName;
        this.avatar = avatar;
    }
    //private String referralCode;



    public void setTotalProjects(int totalProjects) {
        this.totalProjects = totalProjects;
    }

    private int totalProjects;

    public User() {
    }


    public User(String email, String userName, String deparment) {
        this.email = email;
        this.userName = userName;
        this.deparment = deparment;
    }


}
