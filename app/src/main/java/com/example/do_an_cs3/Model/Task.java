package com.example.do_an_cs3.Model;

public class Task {

    private int id;
    private String  taskName;
    private String  taskDescription;
    private String  taskDeadline;
    private String  taskStatus;
    private String  taskEmail;

    private String username;
    private String timeComplete;

    public String getTimeComplete() {
        return timeComplete;
    }

    public void setTimeComplete(String timeComplete) {
        this.timeComplete = timeComplete;
    }

    public Task(String taskName, String taskDescription, String taskDeadline, String taskStatus, String taskEmail, String username, int projectId) {
        this.id = id;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskDeadline = taskDeadline;
        this.taskStatus = taskStatus;
        this.taskEmail = taskEmail;
        this.username = username;
        this.projectId = projectId;

    }

    private int  projectId;

    public Task() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Task(String taskName, String taskDescription, String taskDeadline) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskDeadline = taskDeadline;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(String taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskEmail() {
        return taskEmail;
    }

    public void setTaskEmail(String taskEmail) {
        this.taskEmail = taskEmail;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public Task(int id,String taskName, String taskDescription, String taskDeadline, String taskStatus, String taskEmail, int projectId, String timeComplete) {
        this.id = id;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskDeadline = taskDeadline;
        this.taskStatus = taskStatus;
        this.taskEmail = taskEmail;
        this.projectId = projectId;
        this.timeComplete =timeComplete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Task(String taskName) {
        this.taskName = taskName;
    }
}
