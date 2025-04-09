package org.example.demo2.model;

import java.sql.Timestamp;

public class Notification {
    private int id;
    private String title;
    private String description;
    private String recipient_role;
    private int user_id;
    private Timestamp created_at;
    private String timeAge;
    private boolean read;

    public Notification() {
    }

    public Notification(int id, String title, String description, String recipient_role, int user_id, Timestamp created_at) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.recipient_role = recipient_role;
        this.user_id = user_id;
        this.created_at = created_at;
    }

    public Notification(String title, String description, Timestamp created_at) {
        this.title = title;
        this.description = description;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimeAge(String timeAge) {
        this.timeAge = timeAge;
    }

    public String getTimeAge() {
        return timeAge;
    }

    public String getRecipient_role() {
        return recipient_role;
    }

    public void setRecipientType(String recipient_role) {
        this.recipient_role = recipient_role;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public Timestamp getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Timestamp created_at) {
        this.created_at = created_at;
    }

    public boolean isRead(){
        return read;
    }
    public void setRead(boolean read){this.read = read;}

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", recipientType='" + recipient_role + '\'' +
                ", userId=" + user_id +
                ", createdAt=" + created_at +
                '}';
    }
}
