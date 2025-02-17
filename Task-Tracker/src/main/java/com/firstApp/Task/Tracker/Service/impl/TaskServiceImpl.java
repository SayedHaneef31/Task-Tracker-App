//package com.firstApp.Task.Tracker.Service.impl;
//
//import com.firstApp.Task.Tracker.Entity.Task;
//import com.firstApp.Task.Tracker.Entity.TaskList;
//import com.firstApp.Task.Tracker.Repositories.TaskRepo;
//import com.firstApp.Task.Tracker.Service.TaskService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class TaskServiceImpl implements TaskService {
//
//    @Autowired
//    private TaskRepo taskRepo;
//
//
//    @Override
//    public List<Task> listTask(UUID id) {
//        List<Task> tasks = taskRepo.findAllById(Collections.singleton(id));
//       // return (tasks != null) ? tasks : Collections.emptyList();
//        return tasks;
//    }
//}
