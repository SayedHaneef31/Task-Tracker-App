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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private TaskListRepo taskListRepo;




    @Override
    public List<Task> getTasksByTaskListId(UUID id) {

        return taskRepo.findByTaskListId(id);

        //return taskRepo.findById(id);
//        List<Task> tasks = taskRepo.findAllById(Collections.singleton(id));
//       // return (tasks != null) ? tasks : Collections.emptyList();
//        return tasks;
    }

    @Transactional
    @Override
    public Task createTaskByID(UUID id, Task task) {
        if(null!=task.getId()) throw new IllegalArgumentException("Task already has an id");

        if(null== task.getTitle() || task.getTitle().isBlank()) throw new IllegalArgumentException("Task should always have an title");

        TaskPriority taskPriority= Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);
        // Setting the task priority
        // Optional.ofNullable----- Since task.getPriority() could return null therefore we have put Optional.ofNullable
        //.orElse----- will set the priority in case of null.

        TaskStatus taskStatus= TaskStatus.OPEN;    // Since abhi abhi task create hua hai therefore wo open hi hoga

        TaskList taskList= taskListRepo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Invalid task list id provided"));

        LocalDateTime now= LocalDateTime.now();

        Task taskToSave= new Task(
                null,   // ID will be auto-generated
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now

        );

        Task savedTask = taskRepo.save(taskToSave);

        System.out.println(savedTask);

        return savedTask;
        //return taskRepo.save(taskToSave);



    }

    @Override
    public Optional<Task> getTask(UUID TaskListID, UUID TaskID) {
       return taskRepo.findByTaskListIdAndId(TaskListID,TaskID);
    }

    @Transactional
    @Override
    public Task updateTask(UUID TasKListID, UUID TaskID, Task task) {

        //Create the object of current task and retrive it by id
        Task current=taskRepo.getById(TaskID);
        current.setTitle(task.getTitle());
        current.setDescription(task.getDescription());
        current.setDueDate(task.getDueDate());
        current.setPriority(task.getPriority());

        return taskRepo.save(current);

    }

    @Transactional
    @Override
    public void deleteTaskByID(UUID TasKListID, UUID TaskID) {
        if (!taskListRepo.existsById(TaskID))                //Checking if taskList is present or not...If not throw the erroe
        {
            throw new IllegalArgumentException("TaskList with ID " + TaskID + " not found");
        }
        taskRepo.deleteByTaskListIdAndId(TasKListID,TaskID);
    }
}
