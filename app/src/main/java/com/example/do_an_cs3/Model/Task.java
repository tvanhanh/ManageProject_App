
package com.example.do_an_cs3.Model;
public class Task {

    private String id;
    private String taskName;
    private String taskDescription;
    private String taskDeadline;
    private String taskStatus;
    private String taskEmail;

    private String username;

    private String idProject;

    private String timeComplete;
    private String emailParticipant;
    public Task(){


    }

    public Task(String taskId, String name, String description, String deadline, String status, String email, String idProject, String timeComeplete, String participantEmail) {
        this.id = taskId;
        this.taskName = name;
        this.taskDescription = description;
        this.taskDeadline = deadline;
        this.taskStatus = status;
        this.taskEmail = email;
        this.idProject = idProject;
        this.timeComplete = timeComeplete;
        this.emailParticipant = participantEmail;
    }

    public Task(String id, String taskName, String taskDescription, String taskDeadline, String taskStatus) {
        this.id = id;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskDeadline = taskDeadline;
        this.taskStatus = taskStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdProject() {
        return idProject;
    }

    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }

    public String getTimeComplete() {
        return timeComplete;
    }

    public void setTimeComplete(String timeComplete) {
        this.timeComplete = timeComplete;
    }

    public String getEmailParticipant() {
        return emailParticipant;
    }

    public void setEmailParticipant(String emailParticipant) {
        this.emailParticipant = emailParticipant;
    }
}
