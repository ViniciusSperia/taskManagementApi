package com.speria.vinicius.taskManagementApi.service;

import com.speria.vinicius.taskManagementApi.model.Task;
import com.speria.vinicius.taskManagementApi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");

        task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");
    }

    @Test
    void testFindAllWithPaginationAndSorting() {
        Pageable pageable = PageRequest.of(0, 1, Sort.by("title").ascending());
        Page<Task> page = new PageImpl<>(Arrays.asList(task1), pageable, 2);

        when(taskRepository.findAll(pageable)).thenReturn(page);

        Page<Task> result = taskService.findAll(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("Task 1", result.getContent().get(0).getTitle());
        verify(taskRepository, times(1)).findAll(pageable);
    }

    @Test
    void testSave() {
        when(taskRepository.save(task1)).thenReturn(task1);

        Task savedTask = taskService.save(task1);

        assertEquals("Task 1", savedTask.getTitle());
        verify(taskRepository, times(1)).save(task1);
    }
    @Test
    void testFindById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));

        Optional<Task> task = taskService.findById(1L);

        assertTrue(task.isPresent());
        assertEquals("Task 1", task.get().getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testDelete() {
        taskService.delete(task1);

        verify(taskRepository, times(1)).delete(task1);
    }
}