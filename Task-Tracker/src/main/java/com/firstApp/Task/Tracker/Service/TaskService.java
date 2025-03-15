package com.firstApp.Task.Tracker.Service;

import com.firstApp.Task.Tracker.Entity.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService
{
    List<Task> getTasksByTaskListId(UUID id);

    Optional<Task> getTaskById(UUID id);

    Task createTaskByID(UUID id, Task task);

    Task updateTask(UUID taskId, UUID taskListId,Task task);

    void deleteTaskById(UUID taskId);
}
