package com.example.do_an_cs3.Model;

public class Deparments {

    private String deparment_name;
    private String id;
    private String completeJob;
    private String email;

    public Deparments( String id, String deparment_name, String completeJob, String email) {
        this.deparment_name = deparment_name;
        this.id = id;
        this.completeJob = completeJob;
        this.email = email;
    }

    // Constructor mặc định (không đối số)
    public Deparments() {
    }

    // Constructor với các tham số


    public String getDeparment_name() {
        return deparment_name;
    }

    public void setDeparment_name(String deparment_name) {
        this.deparment_name = deparment_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompleteJob() {
        return completeJob;
    }

    public void setCompleteJob(String completeJob) {
        this.completeJob = completeJob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}