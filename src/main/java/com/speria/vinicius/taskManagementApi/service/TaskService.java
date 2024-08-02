package com.speria.vinicius.taskManagementApi.service;

import com.speria.vinicius.taskManagementApi.model.Task;
import com.speria.vinicius.taskManagementApi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    // Outros métodos
}