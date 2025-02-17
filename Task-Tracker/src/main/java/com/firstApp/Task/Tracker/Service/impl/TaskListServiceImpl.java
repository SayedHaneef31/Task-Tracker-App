package com.firstApp.Task.Tracker.Service.impl;

import com.firstApp.Task.Tracker.Entity.TaskList;
import com.firstApp.Task.Tracker.Repositories.TaskListRepo;
import com.firstApp.Task.Tracker.Service.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {

    @Autowired
    private TaskListRepo taskListRepo;


    @Override
    public List<TaskList> listTaskList() {

        List<TaskList> taskLists = taskListRepo.findAll();
        //return (taskLists != null) ? taskLists : Collections.emptyList();
        return taskLists;
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {

        if(taskList.getId()!= null)
        {
            throw new IllegalArgumentException("Task list has already an id");
        }
        if(null == taskList.getTitle() || taskList.getTitle().isBlank())
        {
            throw new IllegalArgumentException("TaskList must have a title");
        }
        LocalDateTime now = LocalDateTime.now();
        return taskListRepo.save( new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                now,
                now

        ));

    }

    @Override
    public Optional<TaskList> getTaskListById(UUID id) {
        return taskListRepo.findById(id);

    }

    @Override
    public void deleteTaskListById(UUID id) {
        if (!taskListRepo.existsById(id))                //Checking if taskList is present or not...If not throw the erroe
        {
            throw new IllegalArgumentException("TaskList with ID " + id + " not found");
        }
        taskListRepo.deleteById(id);
        //return;
    }

    @Override
    public TaskList updateTaskListByID(UUID id,TaskList taskList) {

        TaskList current=taskListRepo.findById(id).orElseThrow(()->new IllegalArgumentException("Task list not found"));
        // Creating the object for current tasklist by id

        //Setting the title ,description and localdate time
        current.setTitle(taskList.getTitle());
        current.setDescription(taskList.getDescription());
        current.setUpdated(LocalDateTime.now());

//        String title= current.getTitle();
//        String descriptiom= current.getDescription();


        return taskListRepo.save(current);     //Saving the current entry again
    }
}
