package com.firstApp.Task.Tracker.Controller;


import com.firstApp.Task.Tracker.Entity.Task;
import com.firstApp.Task.Tracker.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(path="/api/task-lists/{id}/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> listTasksFromTaskList(@PathVariable("id") UUID id)
    {
//        System.out.println("Fetching tasks for TaskList ID: " + id);
//        Optional<Task> tasks =
        //System.out.println("Tasks fetched: " + tasks.size());
        return taskService.getTasksByTaskListId(id);
    }



    @PostMapping
    public Task createTaskFromID(@PathVariable UUID id,@RequestBody Task task)
    {
       return taskService.createTaskByID(id,task);
    }
}
