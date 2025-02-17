package com.firstApp.Task.Tracker.Repositories;


import com.firstApp.Task.Tracker.Entity.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskListRepo extends JpaRepository<TaskList, UUID>     //Since we are implementing with the help of jpa
// It asks for 1.Type of Entity we are dealing with 2. And the id
//It's empty but String Data Jpa will provide us all the implementation
{

}
