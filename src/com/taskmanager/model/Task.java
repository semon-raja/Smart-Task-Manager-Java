package com.taskmanager.model;

import java.sql.Date;

public class Task {

    private int taskId;
    private int userId;
    private String title;
    private String category;
    private Date deadline;
    private int priority;

    public int getTaskId() { return taskId; }
    public void setTaskId(int taskId) { this.taskId = taskId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Date getDeadline() { return deadline; }
    public void setDeadline(Date deadline) { this.deadline = deadline; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
}
