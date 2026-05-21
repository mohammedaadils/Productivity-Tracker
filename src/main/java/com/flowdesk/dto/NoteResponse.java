package com.flowdesk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NoteResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
}