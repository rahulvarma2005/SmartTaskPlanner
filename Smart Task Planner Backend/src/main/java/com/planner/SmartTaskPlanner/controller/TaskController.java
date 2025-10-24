package com.planner.SmartTaskPlanner.controller;

import com.planner.SmartTaskPlanner.dto.GoalRequest;
import com.planner.SmartTaskPlanner.dto.TaskResponse;
import com.planner.SmartTaskPlanner.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/generate")
    public ResponseEntity<List<TaskResponse>> generateTasks(@RequestBody GoalRequest goalRequest) {
        List<TaskResponse> tasks = taskService.generateAndSaveTasks(goalRequest);
        return ResponseEntity.ok(tasks);
    }
}