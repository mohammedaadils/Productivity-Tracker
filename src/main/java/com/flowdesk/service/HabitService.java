package com.flowdesk.service;

import com.flowdesk.dto.HabitResponse;
import com.flowdesk.model.Habit;
import com.flowdesk.model.User;
import com.flowdesk.repository.HabitRepository;
import com.flowdesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HabitService {

    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private HabitResponse toResponse(Habit h) {
        return new HabitResponse(h.getId(), h.getHabitName(), h.getStreak(), h.isCompletedToday());
    }

    public List<HabitResponse> getHabits(String email) {
        User user = getUser(email);
        List<Habit> habits = habitRepository.findByUserId(user.getId());

        // Auto-create default habits if none exist
        if (habits.isEmpty()) {
            List<String> defaults = List.of("Gym", "Coding", "Sleep", "Water");
            habits = defaults.stream().map(name -> habitRepository.save(
                    Habit.builder().habitName(name).user(user).build()
            )).collect(Collectors.toList());
        }

        return habits.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public HabitResponse markComplete(String email, Long id) {
        Habit habit = habitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habit not found"));
        habit.setCompletedToday(true);
        habit.setStreak(habit.getStreak() + 1);
        return toResponse(habitRepository.save(habit));
    }

    public HabitResponse resetHabit(String email, Long id) {
        Habit habit = habitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habit not found"));
        habit.setCompletedToday(false);
        return toResponse(habitRepository.save(habit));
    }
}