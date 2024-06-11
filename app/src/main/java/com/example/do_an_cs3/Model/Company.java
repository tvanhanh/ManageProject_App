package com.example.do_an_cs3.Model;



public class Company {
    private String address;
    private  String companyId;
    private String companyName;
    private String email;
    private String field;
    private String logoImg;
    private String phoneNumber;


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Company(String address, String companyId, String companyName, String email, String field, String logoImg, String phoneNumber) {
        this.address = address;
        this.companyId = companyId;
        this.companyName = companyName;
        this.email = email;
        this.field = field;
        this.logoImg = logoImg;
        this.phoneNumber = phoneNumber;
    }
    // Constructor không đối số
    public Company() {
    }
}
