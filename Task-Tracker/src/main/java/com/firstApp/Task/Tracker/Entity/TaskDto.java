package com.firstApp.Task.Tracker.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class TaskDto {
    private UUID id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private TaskStatus status;
    private TaskPriority priority;
    private UUID taskListId; // Instead of full TaskList object, return only its ID

    public TaskDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.dueDate = task.getDueDate();
        this.status = task.getStatus();
        this.priority = task.getPriority();
        this.taskListId = task.getTaskList().getId();
    }
}
