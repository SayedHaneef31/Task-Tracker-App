package com.firstApp.Task.Tracker.Service.impl;

import com.firstApp.Task.Tracker.Entity.Task;
import com.firstApp.Task.Tracker.Entity.TaskList;
import com.firstApp.Task.Tracker.Entity.TaskPriority;
import com.firstApp.Task.Tracker.Entity.TaskStatus;
import com.firstApp.Task.Tracker.Repositories.TaskListRepo;
import com.firstApp.Task.Tracker.Repositories.TaskRepo;
import com.firstApp.Task.Tracker.Service.TaskService;
import jakarta.persistence.EntityNotFoundException;
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

    @Override
    public Optional<Task> getTaskById(UUID id) {
        return taskRepo.findById(id);
    }

    @Transactional
    @Override
    public Task createTaskByID(UUID taskListId, Task task) {
        if(null!=task.getId()) throw new IllegalArgumentException("Task already has an id");

        if(null== task.getTitle() || task.getTitle().isBlank()) throw new IllegalArgumentException("Task should always have an title");

        TaskPriority taskPriority= Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);
        // Setting the task priority
        // Optional.ofNullable----- Since task.getPriority() could return null therefore we have put Optional.ofNullable
        //.orElse----- will set the priority in case of null.

        TaskStatus taskStatus= TaskStatus.OPEN;    // Since abhi abhi task create hua hai therefore wo open hi hoga

        TaskList taskList= taskListRepo.findById(taskListId)
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
    @Transactional
    public Task updateTask(UUID taskId, UUID taskListId,Task updatedTask) {
        // Find the existing task
        Task existingTask = taskRepo.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        System.out.println("Existing task ID: " + existingTask.getId());
        System.out.println("Updated task ID: " + updatedTask.getId());
        // Check if the task belongs to the given taskListId
        if (!existingTask.getTaskList().getId().equals(taskListId)) {
            throw new IllegalArgumentException("Task does not belong to the provided TaskList");
        }

        // Update only the fields that are allowed
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setPriority(updatedTask.getPriority());
        existingTask.setUpdated(LocalDateTime.now()); // Update timestamp

        // Save and return the updated task
        return taskRepo.save(existingTask);
    }

    @Override
    public void deleteTaskById(UUID taskId) {
        taskRepo.deleteById(taskId);
    }
}
