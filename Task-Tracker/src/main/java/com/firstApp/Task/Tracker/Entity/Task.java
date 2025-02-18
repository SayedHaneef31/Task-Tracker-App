package com.firstApp.Task.Tracker.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="tasks")             //Creates the name of the table
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)    //   JPA will automatically generate the uuid if it's null on its own
    @Column(name="id",updatable = false,nullable = false)
    private UUID id;

    @Column(name = "title",nullable = false)
    private String title;

    public Task(UUID id, String title, String description, LocalDateTime dueDate, TaskStatus status, TaskPriority priority, TaskList taskList, LocalDateTime updated, LocalDateTime created) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
        this.taskList = taskList;
        this.updated = updated;
        this.created = created;
    }

    @Column(name="description")
    private String description;

    @Column(name = "dueDate")
    private LocalDateTime dueDate;

    @Column(name = "status",nullable = false)
    private TaskStatus status;

    @Column(name = "priority",nullable = false)
    private TaskPriority priority;

    @JsonBackReference  // ✅ Prevent infinite recursion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_list_id", nullable = false)
    private TaskList taskList;

//  1.  @ManyToOne(fetch = FetchType.LAZY) → Many Task objects belong to one TaskList.
//  2.  Lazy Loading: Doesn't fetch TaskList data unless explicitly accessed, Improves performance.
//  3.  @JoinColumn(name = "task_list_id") → Maps the foreign key column in the Task table.

    @Column(name = "updated",nullable = false)
    private LocalDateTime updated;

    @Column(name = "created",nullable = false)
    private LocalDateTime created;


    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", status=" + status +
                ", priority=" + priority +
                ", taskList=" + taskList +
                ", updated=" + updated +
                ", created=" + created +
                '}';
    }

    public Task() {
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

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title) && Objects.equals(description, task.description) && Objects.equals(dueDate, task.dueDate) && status == task.status && priority == task.priority && Objects.equals(taskList, task.taskList) && Objects.equals(updated, task.updated) && Objects.equals(created, task.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, dueDate, status, priority, taskList, updated, created);
    }
}
