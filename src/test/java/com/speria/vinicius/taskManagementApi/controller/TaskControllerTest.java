package com.speria.vinicius.taskManagementApi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.speria.vinicius.taskManagementApi.model.Task;
import com.speria.vinicius.taskManagementApi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        taskRepository.deleteAll();

        task1 = new Task();
        task1.setTitle("Task 1");
        task1.setDescription("Description 1");
        task1.setStatus("Pending");
        task1.setPriority("High");

        task2 = new Task();
        task2.setTitle("Task 2");
        task2.setDescription("Description 2");
        task2.setStatus("Completed");
        task2.setPriority("Low");

        taskRepository.saveAll(Arrays.asList(task1, task2));
    }

    @Test
    void testCreateTaskWithInvalidData() throws Exception {
        Task invalidTask = new Task();
        // Deliberadamente omitindo os campos obrigatórios para validar

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTask)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title is mandatory"))
                .andExpect(jsonPath("$.description").value("Description is mandatory"))
                .andExpect(jsonPath("$.status").value("Status is mandatory"))
                .andExpect(jsonPath("$.priority").value("Priority is mandatory"));
    }

    @Test
    void testCreateTask() throws Exception {
        Task newTask = new Task();
        newTask.setTitle("New Task");
        newTask.setDescription("New Description");
        newTask.setStatus("Pending");
        newTask.setPriority("Medium");

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("New Task")));
    }
}