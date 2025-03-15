package com.firstApp.Task.Tracker.Entity;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TaskListDto {

    private UUID id;
    private String title;
    private String description;
    private List<TaskDto> tasks;

    public TaskListDto(TaskList taskList) {
        this.id = taskList.getId();
        this.title = taskList.getTitle();
        this.description = taskList.getDescription();
        this.tasks = taskList.getTasks().stream().map(TaskDto::new).toList();
    }
}
