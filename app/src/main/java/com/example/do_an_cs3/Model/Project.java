package com.example.do_an_cs3.Model;

public class Project {
    private int id;
    private String name;
    private String description;
    private String deadline;

    private String creationTime;

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    private int views;

    private int percentCompleted;
    private String email;
    private String status;
    private int department;

    // Constructors



    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getPercentCompleted() {
        return percentCompleted;
    }

    public void setPercentCompleted(int percentCompleted) {
        this.percentCompleted = percentCompleted;
    }

    public Project(int id, String name, String description, String deadline, String creationTime, int views, int percentCompleted, String email, String status, int department) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.creationTime = creationTime;
        this.views = views;
        this.percentCompleted = percentCompleted;
        this.email = email;
        this.status = status;
        this.department = department;
    }

    public Project() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }
}