package com.task.tms.dto;

import lombok.Data;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private String assigneeId;
}



