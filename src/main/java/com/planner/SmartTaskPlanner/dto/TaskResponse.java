package com.planner.SmartTaskPlanner.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TaskResponse {
    private Long id;
    private String taskDescription;
    private String deadline;
    private String status;
    private String dependencies;
}

