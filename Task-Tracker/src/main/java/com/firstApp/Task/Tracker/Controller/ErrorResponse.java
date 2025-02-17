package com.firstApp.Task.Tracker.Controller;//package com.firstApp.Task.Tracker.Controller;
//package com.firstApp.Task.Tracker.Exception;

public class ErrorResponse {
    private int status;
    private String message;
    private String description;

    public ErrorResponse(int status, String message, String description) {
        this.status = status;
        this.message = message;
        this.description = description;
    }

    // Getters and Setters
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public String getDescription() { return description; }
}
