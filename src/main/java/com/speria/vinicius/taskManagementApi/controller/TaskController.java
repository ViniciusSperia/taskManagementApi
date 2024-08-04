package com.speria.vinicius.taskManagementApi.controller;

import com.speria.vinicius.taskManagementApi.model.Task;
import com.speria.vinicius.taskManagementApi.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public Page<Task> getAllTasks(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id,desc") String[] sort) {
        Sort.Direction dir = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
        return taskService.findAll(pageable);
    }

    @PostMapping
    public Task createTask(@Valid @RequestBody Task task) {
        return taskService.save(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task taskDetails) {
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