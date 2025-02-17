//package com.firstApp.Task.Tracker.Controller;
//
//
//import com.firstApp.Task.Tracker.Entity.Task;
//import com.firstApp.Task.Tracker.Service.TaskService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//
//@CrossOrigin(origins = "http://localhost:5173")
//@RestController
//@RequestMapping(path="/api/task-lists")
//public class TaskController {
//
//    @Autowired
//    private TaskService taskService;
//
//    @GetMapping("/{id}")
//    public List<Task> listTaskList(@PathVariable("id") UUID id)
//    {
//
//        return taskService.listTask(id);
//
//    }
//}
