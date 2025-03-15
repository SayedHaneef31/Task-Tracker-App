package com.firstApp.Task.Tracker.Controller;


import com.firstApp.Task.Tracker.Entity.TaskList;
import com.firstApp.Task.Tracker.Service.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@CrossOrigin(origins = "http://localhost:5173")     //Enables us to connect to the frontend code
@RestController           // Tells it is a rest controller
@RequestMapping(path="/api/task-lists")
public class TaskListController {

    //private static final Logger logger = LoggerFactory.getLogger(TaskListController.class);
    @Autowired
    private TaskListService taskListService;

    @GetMapping
    public List<TaskList> getTaskList()
    {
        // taskListService.listTaskList();
        List<TaskList> taskLists = taskListService.listTaskList();
        return Optional.ofNullable(taskLists).orElse(Collections.emptyList());



    }

    @GetMapping(path="/{task_list_id}")                       //@PathVariable = Jb link se value retive krni ho tab we use path variable
    public Optional<TaskList> getAllTaskList(@PathVariable("task_list_id") UUID task_list_id)
    {
        return taskListService.getTaskListById(task_list_id);
        //return taskListService.listTaskList();

    }
//    @GetMapping("/{id}")
//    public Optional<TaskList> getTaskListById(@PathVariable UUID id) {
//        return taskListService.getTaskListById(id);
//    }


    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping
    public TaskList createTaskList(@RequestBody TaskList taskList)
    {
        return taskListService.createTaskList(taskList);
    }


    @DeleteMapping("/{id}")
    public void deletingTaskListById(@PathVariable("id") UUID id)
    {
        taskListService.deleteTaskListById(id);
    }

    @PutMapping("/{taskListID}")
    public TaskList updateTaskListById(
            @PathVariable("taskListID") UUID uuid,     //to access the "taskListID" from the url we need to put @PathVariable
            @RequestBody TaskList taskList             // raw form me jo hma data bhejenge or the data comming from the frontend
    )
    {
        return taskListService.updateTaskListByID(uuid,taskList);
    }
}
