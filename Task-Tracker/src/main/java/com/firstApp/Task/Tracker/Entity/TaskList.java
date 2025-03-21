package com.firstApp.Task.Tracker.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity

@Table(name="task_lists")
public class TaskList {
    public UUID getId() {
        return id;
    }

    public TaskList() {
    }


    public TaskList(UUID id, String title, String description, List<Task> tasks, LocalDateTime created, LocalDateTime updated) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.tasks = tasks;
        this.created = created;
        this.updated = updated;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

//    public void setTasks(List<Task> tasks) {
//        this.tasks = tasks;
//    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        for (Task task : tasks) {
            task.setTaskList(this);
        }
    }


    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)    //   JPA will automatically generate the uuid if it's null on its own
    @Column(name="id",updatable = false,nullable = false)
    private UUID id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name="description")
    private String description;

    @JsonIgnore  // ✅ Prevent infinite recursion
    @OneToMany(mappedBy = "taskList", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Task> tasks;

//  1.  OneToMany(mappedBy = "taskList") → One TaskList has many Task records.
//  2.  mappedBy = "taskList" → The taskList field in Task is the owner of the relationship.
//  3.  Cascade Behavior:
//      a) REMOVE → Deleting TaskList deletes all associated Task records.
//      b) PERSIST → Saving TaskList automatically saves all related Task records.



    @Column(name = "created",nullable = false)
    private LocalDateTime created;

    @Column(name = "updated",nullable = false)
    private LocalDateTime updated;
}
