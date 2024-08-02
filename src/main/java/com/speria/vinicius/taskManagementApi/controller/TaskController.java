package com.speria.vinicius.taskManagementApi.controller;

import com.speria.vinicius.taskManagementApi.model.Task;
import com.speria.vinicius.taskManagementApi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.findAll();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.save(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Optional<Task> taskOptional = taskService.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setStatus(taskDetails.getStatus());
            task.setCreatedAt(taskDetails.getCreatedAt());
            task.setCompletedAt(taskDetails.getCompletedAt());
            task.setPriority(taskDetails.getPriority());
            final Task updatedTask = taskService.save(task);
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        Optional<Task> taskOptional = taskService.findById(id);
        if (taskOptional.isPresent()) {
            taskService.delete(taskOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}