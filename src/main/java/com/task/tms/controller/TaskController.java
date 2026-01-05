package com.task.tms.controller;

import com.task.tms.dto.*;
import com.task.tms.model.Task;
import com.task.tms.repository.TaskRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Task API", description = "Endpoints for task management")
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // Create Task - USER, ADMIN
    @PostMapping
    public TaskResponse createTask(@RequestBody TaskRequest request) {
        Task task = new Task();
        task.setId(UUID.randomUUID().toString());
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus("OPEN");
        task.setAssigneeId(request.getAssigneeId());
        taskRepository.save(task);

        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setStatus(task.getStatus());
        response.setAssigneeId(task.getAssigneeId());
        return response;
    }

    // Update Task Status - USER, ADMIN
    @PutMapping("/{id}/status")
    public TaskResponse updateTaskStatus(@PathVariable String id,
                                         @RequestBody TaskStatusUpdateRequest request) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setStatus(request.getStatus());
        taskRepository.save(task);

        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setStatus(task.getStatus());
        response.setAssigneeId(task.getAssigneeId());
        return response;
    }
}