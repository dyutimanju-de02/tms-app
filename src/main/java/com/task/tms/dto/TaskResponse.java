package com.task.tms.dto;

import lombok.Data;

@Data
public class TaskResponse {
    private String id;
    private String title;
    private String status;
    private String assigneeId;
}