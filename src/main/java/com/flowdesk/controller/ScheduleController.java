package com.flowdesk.controller;

import com.flowdesk.dto.ScheduleRequest;
import com.flowdesk.dto.ScheduleResponse;
import com.flowdesk.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> getByDate(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(scheduleService.getByDate(userDetails.getUsername(), date));
    }

    @PostMapping
    public ResponseEntity<ScheduleResponse> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ScheduleRequest request) {
        return ResponseEntity.ok(scheduleService.create(userDetails.getUsername(), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        scheduleService.delete(userDetails.getUsername(), id);
        return ResponseEntity.noContent().build();
    }
}