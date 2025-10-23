package com.planner.SmartTaskPlanner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.SmartTaskPlanner.dto.GoalRequest;
import com.planner.SmartTaskPlanner.dto.TaskResponse;
import com.planner.SmartTaskPlanner.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGenerateTasks() throws Exception {
        // Prepare sample GoalRequest
        GoalRequest goalRequest = new GoalRequest();
        goalRequest.setGoal("Learn Spring Boot");

        // Prepare sample TaskResponse list with String deadlines
        TaskResponse task1 = new TaskResponse();
        task1.setId(1L);
        task1.setTaskDescription("Set up Spring Boot project");
        task1.setDeadline("in 2 days"); // Corrected to String
        task1.setStatus("To Do");
        task1.setDependencies(null);

        TaskResponse task2 = new TaskResponse();
        task2.setId(2L);
        task2.setTaskDescription("Learn Spring Boot basics");
        task2.setDeadline("in 5 days"); // Corrected to String
        task2.setStatus("To Do");
        task2.setDependencies("[Depends on: Task #1]");

        List<TaskResponse> mockTasks = Arrays.asList(task1, task2);

        // Mock the service method
        when(taskService.generateAndSaveTasks(any(GoalRequest.class))).thenReturn(mockTasks);

        // Perform POST request and verify response
        mockMvc.perform(post("/api/tasks/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(goalRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].taskDescription").value("Set up Spring Boot project"))
                .andExpect(jsonPath("$[0].deadline").value("in 2 days")) // Assert String value
                .andExpect(jsonPath("$[0].status").value("To Do"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].taskDescription").value("Learn Spring Boot basics"))
                .andExpect(jsonPath("$[1].deadline").value("in 5 days")) // Assert String value
                .andExpect(jsonPath("$[1].status").value("To Do"))
                .andExpect(jsonPath("$[1].dependencies").value("[Depends on: Task #1]"));
    }
}
