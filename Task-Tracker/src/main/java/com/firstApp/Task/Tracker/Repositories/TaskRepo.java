package com.firstApp.Task.Tracker.Repositories;


import com.firstApp.Task.Tracker.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


//It will provide all the default and basic functionalities

@Repository
public interface TaskRepo extends JpaRepository<Task, UUID> {
    //JpaRepository<Task, UUID> means it provides built-in CRUD operations for the Task entity with a primary key of type UUID.



}
