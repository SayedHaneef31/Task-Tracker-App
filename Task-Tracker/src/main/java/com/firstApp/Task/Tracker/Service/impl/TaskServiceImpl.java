package com.firstApp.Task.Tracker.Service.impl;

import com.firstApp.Task.Tracker.Entity.Task;
import com.firstApp.Task.Tracker.Entity.TaskList;
import com.firstApp.Task.Tracker.Entity.TaskPriority;
import com.firstApp.Task.Tracker.Entity.TaskStatus;
import com.firstApp.Task.Tracker.Repositories.TaskListRepo;
import com.firstApp.Task.Tracker.Repositories.TaskRepo;
import com.firstApp.Task.Tracker.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private TaskListRepo taskListRepo;


   // @Override
//    public List<Task> listTask(UUID id) {
//        List<Task> tasks = taskRepo.findAllById(Collections.singleton(id));
//       // return (tasks != null) ? tasks : Collections.emptyList();
//        return tasks;
//    }

   // @Override
    //public Task createTaskByID(UUID id, Task task) {
//        if(null!=task.getId()) throw new IllegalArgumentException("Task already has an id");
//
//        if(null== task.getTitle() || task.getTitle().isBlank()) throw new IllegalArgumentException("Task should always have an title");
//
//        TaskPriority taskPriority= Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);
//        // Setting the task priority
//        // Optional.ofNullable----- Since task.getPriority() could return null therefore we have put Optional.ofNullable
//        //.orElse----- will set the priority in case of null.
//
//        TaskStatus taskStatus= TaskStatus.OPEN;    // Since abhi abhi task create hua hai therefore wo open hi hoga
//
//        TaskList taskList= taskListRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid task list id provided"));
//
//        LocalDateTime now= LocalDateTime.now();
//
//        Task taskToSave= new Task(
//                null,
//                task.getTitle(),
//                task.getDescription(),
//                task.getDueDate(),
//                taskStatus,
//                taskPriority,
//                taskList,
//                now,
//                now
//
//        );
//
//        return taskRepo.save(taskToSave);
//        return null;
//    }
}
