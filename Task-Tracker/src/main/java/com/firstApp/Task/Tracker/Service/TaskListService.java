package com.firstApp.Task.Tracker.Service;

import com.firstApp.Task.Tracker.Entity.TaskList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskListService
{
    List<TaskList> listTaskList();

    TaskList createTaskList(TaskList taskList);

    Optional<TaskList> getTaskListById(UUID id);

    void deleteTaskListById(UUID id);

    TaskList updateTaskListByID(UUID id ,TaskList taskList);

}
