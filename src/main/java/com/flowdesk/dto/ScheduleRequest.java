package com.flowdesk.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleRequest {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String activity;
}