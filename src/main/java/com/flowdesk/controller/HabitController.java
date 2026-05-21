package com.flowdesk.controller;

import com.flowdesk.dto.HabitResponse;
import com.flowdesk.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HabitController {

    private final HabitService habitService;

    @GetMapping
    public ResponseEntity<List<HabitResponse>> getHabits(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(habitService.getHabits(userDetails.getUsername()));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<HabitResponse> markComplete(@AuthenticationPrincipal UserDetails userDetails,
                                                      @PathVariable Long id) {
        return ResponseEntity.ok(habitService.markComplete(userDetails.getUsername(), id));
    }

    @PatchMapping("/{id}/reset")
    public ResponseEntity<HabitResponse> reset(@AuthenticationPrincipal UserDetails userDetails,
                                               @PathVariable Long id) {
        return ResponseEntity.ok(habitService.resetHabit(userDetails.getUsername(), id));
    }
}