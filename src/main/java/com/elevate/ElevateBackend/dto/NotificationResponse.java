package com.elevate.ElevateBackend.dto;

public class NotificationResponse {

    private String title;
    private String message;
    private String type;

    public NotificationResponse() {
    }

    public NotificationResponse(String title,
                                String message,
                                String type) {

        this.title = title;
        this.message = message;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(String type) {
        this.type = type;
    }

}