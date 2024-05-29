package com.example.do_an_cs3.Model;

import android.content.Intent;

public class User {
    private String email;
    private String userName;

    public int getTotalProjects() {
        return totalProjects;
    }

    private String phoneNumber;

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, String avatar) {
        this.userName = userName;
        this.avatar = avatar;
    }

    private String address;
    private String referralCode;
    private String avatar;
    private int deparment;
    private String role;

    public void setTotalProjects(int totalProjects) {
        this.totalProjects = totalProjects;
    }

    private int totalProjects;

    public User() {
    }

    public User(String userName, String phoneNumber, String address, String referralCode, String avatar, int deparment, String role) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.referralCode = referralCode;
        this.avatar = avatar;
        this.deparment = deparment;
        this.role = role;
    }

    public User(String email, String userName, int deparment, int totalProjects  ) {
        this.email = email;
        this.userName = userName;
        this.deparment = deparment;
        this.totalProjects = totalProjects;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getDeparment() {
        return deparment;
    }

    public void setDeparment(int deparment) {
        this.deparment = deparment;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
