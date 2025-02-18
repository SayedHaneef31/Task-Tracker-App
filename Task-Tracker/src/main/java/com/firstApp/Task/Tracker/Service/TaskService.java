package com.firstApp.Task.Tracker.Service;

import com.firstApp.Task.Tracker.Entity.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService
{
    List<Task> getTasksByTaskListId(UUID id);



    Task createTaskByID(UUID id, Task task);
}
