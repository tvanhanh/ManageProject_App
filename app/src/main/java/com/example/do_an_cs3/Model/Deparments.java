package com.example.do_an_cs3.Model;

public class Deparments {
    private String deparment_name;
    private String number;
    private String completeJob;

    public Deparments(String deparment_name, String number, String completeJob) {
        this.deparment_name = deparment_name;
        this.number = number;
        this.completeJob = completeJob;
    }

    public String getName() {
        return deparment_name;
    }

    public String getNumber() {
        return number;
    }

    public String getCompleteJob() {
        return completeJob;
    }
}

