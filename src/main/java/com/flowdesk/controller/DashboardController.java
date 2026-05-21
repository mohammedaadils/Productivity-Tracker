package com.flowdesk.controller;

import com.flowdesk.repository.HabitRepository;
import com.flowdesk.repository.TaskRepository;
import com.flowdesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private final TaskRepository taskRepository;
    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"))
                .getId();

        long totalTasks = taskRepository.findByUserId(userId).size();
        long completedTasks = taskRepository.findByUserIdAndStatus(userId, "COMPLETED").size();
        long pendingTasks = totalTasks - completedTasks;

        long habitsCompleted = habitRepository.findByUserId(userId)
                .stream().filter(h -> h.isCompletedToday()).count();

        long totalStreak = habitRepository.findByUserId(userId)
                .stream().mapToLong(h -> h.getStreak()).sum();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalTasks", totalTasks);
        stats.put("completedTasks", completedTasks);
        stats.put("pendingTasks", pendingTasks);
        stats.put("habitsCompletedToday", habitsCompleted);
        stats.put("totalHabitStreak", totalStreak);

        return ResponseEntity.ok(stats);
    }
}