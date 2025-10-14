package com.planner.SmartTaskPlanner.service;

import com.planner.SmartTaskPlanner.dto.GoalRequest;
import com.planner.SmartTaskPlanner.dto.TaskResponse;
import com.planner.SmartTaskPlanner.model.Task;
import com.planner.SmartTaskPlanner.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final WebClient webClient;
    private final String geminiApiKey;
    private final String geminiApiUrl;
    private final String promptTemplate;

    public TaskService(TaskRepository taskRepository,
                       @Value("${gemini.api.key}") String geminiApiKey,
                       @Value("${gemini.api.url}") String geminiApiUrl,
                       @Value("${gemini.api.prompt.template}") String promptTemplate) {
        this.taskRepository = taskRepository;
        this.webClient = WebClient.builder().build();
        this.geminiApiKey = geminiApiKey;
        this.geminiApiUrl = geminiApiUrl;
        this.promptTemplate = promptTemplate;
    }

    public List<TaskResponse> generateAndSaveTasks(GoalRequest goalRequest) {
        String prompt = promptTemplate.replace("{goal}", goalRequest.getGoal());

        Map<String, Object> requestBody = Map.of(
                "model", "gemini-2.5-pro", // Using a model from your available list
                "contents", List.of(
                        Map.of(
                                "role", "user",
                                "parts", List.of(
                                        Map.of("text", prompt)
                                )
                        )
                )
        );

        Map<String, Object> response = webClient.post()
                .uri(geminiApiUrl + "?key=" + geminiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        String generatedText = extractTextFromResponse(response);
        List<Task> savedTasks = parseAndSaveTasks(generatedText, goalRequest.getGoal());

        return savedTasks.stream()
                .map(this::convertToTaskResponse)
                .toList();
    }

    private String extractTextFromResponse(Map<String, Object> response) {
        try {
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
                List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                if (parts != null && !parts.isEmpty()) {
                    return (String) parts.get(0).get("text");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract text from Gemini API response", e);
        }
        return "";
    }

    private List<Task> parseAndSaveTasks(String generatedText, String originalGoal) {
        List<Task> tasks = new ArrayList<>();
        String[] lines = generatedText.split("\n");

        Pattern taskPattern = Pattern.compile("^\\d+\\.\\s*(.+)");
        // This new pattern only matches strings that look like a relative time.
        Pattern deadlinePattern = Pattern.compile("\\((in \\d+ (?:hour|day|week|month)s?)\\)");
        Pattern dependencyPattern = Pattern.compile("(\\[Depends on: .*?\\])");

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            Matcher taskMatcher = taskPattern.matcher(line);
            if (taskMatcher.find()) {
                String taskText = taskMatcher.group(1);
                String deadline = null;
                String dependencies = null;

                // Extract relative deadline using the new, safer pattern
                Matcher deadlineMatcher = deadlinePattern.matcher(taskText);
                if (deadlineMatcher.find()) {
                    deadline = deadlineMatcher.group(1); // Get the captured deadline text
                    // Use a more specific replace to avoid removing incorrect parentheses
                    taskText = taskText.replace("(" + deadline + ")", "").trim();
                }

                // Extract single or multiple dependencies
                Matcher dependencyMatcher = dependencyPattern.matcher(taskText);
                if (dependencyMatcher.find()) {
                    dependencies = dependencyMatcher.group(1);
                    taskText = taskText.replaceAll("\\s*\\[Depends on: .*?\\]", "").trim();
                }

                // Create and save the task
                Task task = new Task();
                task.setGoal(originalGoal);
                task.setTaskDescription(taskText);
                task.setDeadline(deadline);
                task.setDependencies(dependencies);
                task.setStatus("To Do");

                tasks.add(taskRepository.save(task));
            }
        }
        return tasks;
    }

    private TaskResponse convertToTaskResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTaskDescription(task.getTaskDescription());
        response.setDeadline(task.getDeadline());
        response.setStatus(task.getStatus());
        response.setDependencies(task.getDependencies());
        return response;
    }
}

