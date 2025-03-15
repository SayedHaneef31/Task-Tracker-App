package com.firstApp.Task.Tracker.Controller;


import com.firstApp.Task.Tracker.Entity.Task;
import com.firstApp.Task.Tracker.Service.TaskListService;
import com.firstApp.Task.Tracker.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(path="/api/task-lists/{taskListId}/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskListService taskListService;

    @GetMapping
    public List<Task> listTasksFromTaskList(@PathVariable("taskListId") UUID taskListId)
    {
//        System.out.println("Fetching tasks for TaskList ID: " + id);
//        Optional<Task> tasks =
        //System.out.println("Tasks fetched: " + tasks.size());
        return taskService.getTasksByTaskListId(taskListId);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTaskInsideTaskList(@PathVariable UUID taskListId,
                                                         @PathVariable UUID taskId,
                                                         @RequestBody Task updatedTask)
    {
        System.out.println("I am here!!!!!!! in put mapping");


        System.out.println("Update request - Task ID: " + taskId);
        System.out.println("Update request - Task object ID: " + updatedTask.getId());

        Task task = taskService.updateTask(taskId, taskListId, updatedTask);
        return ResponseEntity.ok(task);
//        Optional<Task> localTask=taskService.getTaskById(taskId);    //task id is valid
//        if(localTask.isEmpty())  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        //var tasklList=taskListService.getTaskListById(taskListId);
//        //localTask.
//
//        Task existingTask = localTask.get();
//        // Check if the task belongs to the given task list
//        if (!existingTask.getTaskList().getId().equals(taskListId)) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(null);
//        }
//
//        // Update task properties
//        existingTask.setTitle(updatedTask.getTitle());
//        existingTask.setDescription(updatedTask.getDescription());
//        existingTask.setDueDate(updatedTask.getDueDate());
//        existingTask.setStatus(updatedTask.getStatus());
//        existingTask.setPriority(updatedTask.getPriority());
//        existingTask.setUpdated(LocalDateTime.now());
//
//        Task savedTask = taskService.updateTask(existingTask);
//        return ResponseEntity.ok(savedTask);
    }


    @PostMapping
    public Task createTaskFromID(@PathVariable UUID taskListId,@RequestBody Task task)
    {
        var tasklList=taskListService.getTaskListById(taskListId);
        String title=tasklList.get().getTitle();

        System.out.println("Inside Post method of task with TaskList ID: " + taskListId+ " and title= "+ title);
       return taskService.createTaskByID(taskListId,task);
    }




    @DeleteMapping("/{taskId}")
    public void deleteTaskById(@PathVariable UUID taskId)
    {
        taskService.deleteTaskById(taskId);
    }
}
