package com.flowdesk.service;

import com.flowdesk.dto.ScheduleRequest;
import com.flowdesk.dto.ScheduleResponse;
import com.flowdesk.model.Schedule;
import com.flowdesk.model.User;
import com.flowdesk.repository.ScheduleRepository;
import com.flowdesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private ScheduleResponse toResponse(Schedule s) {
        return new ScheduleResponse(s.getId(), s.getDate(), s.getStartTime(), s.getEndTime(), s.getActivity());
    }

    public List<ScheduleResponse> getByDate(String email, LocalDate date) {
        return scheduleRepository.findByUserIdAndDate(getUser(email).getId(), date)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ScheduleResponse create(String email, ScheduleRequest request) {
        Schedule schedule = Schedule.builder()
                .date(request.getDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .activity(request.getActivity())
                .user(getUser(email))
                .build();
        return toResponse(scheduleRepository.save(schedule));
    }

    public void delete(String email, Long id) {
        scheduleRepository.deleteById(id);
    }
}