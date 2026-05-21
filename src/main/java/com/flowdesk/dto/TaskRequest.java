package com.flowdesk.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private String priority;   // LOW, MEDIUM, HIGH
    private String category;
    private LocalDate deadline;
}