package com.flowdesk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HabitResponse {
    private Long id;
    private String habitName;
    private int streak;
    private boolean completedToday;
}