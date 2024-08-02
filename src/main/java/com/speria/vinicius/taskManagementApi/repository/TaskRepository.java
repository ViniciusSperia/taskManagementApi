package com.speria.vinicius.taskManagementApi.repository;

import com.speria.vinicius.taskManagementApi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
