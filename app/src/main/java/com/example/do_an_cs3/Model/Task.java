
package com.example.do_an_cs3.Model;
public class Task {
    private String id;
    private String name;
    private String description;
    private String deadline;
    private String status;
    private String email;
    private String projectId;
    private String timeComplete;

    // Default constructor required for calls to DataSnapshot.getValue(Task.class)
    public Task() {
    }

    public Task(String id, String name, String description, String deadline, String status, String email, String projectId, String timeComeplete) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
        this.email = email;
        this.projectId = projectId;
        this.timeComplete = timeComeplete;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimeComplete() {
        return timeComplete;
    }

    public void setTimeComplete(String timeComplete) {

        this.timeComplete = timeComplete;
    }

    public String getProjectId() {
        return projectId;
    }

}

//   // public void setProjectId(String projectId) {
//        this.projectId = projectId;
//    }

//    public String getUsername() {
//        return getUsername();
//    }

