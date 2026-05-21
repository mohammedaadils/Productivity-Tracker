package com.flowdesk.service;

import com.flowdesk.dto.TaskRequest;
import com.flowdesk.dto.TaskResponse;
import com.flowdesk.model.Task;
import com.flowdesk.model.User;
import com.flowdesk.repository.TaskRepository;
import com.flowdesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getStatus(),
                task.getCategory(),
                task.getDeadline()
        );
    }

    public List<TaskResponse> getAllTasks(String email) {
        return taskRepository.findByUserId(getUser(email).getId())
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public TaskResponse createTask(String email, TaskRequest request) {
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .status("PENDING")
                .category(request.getCategory())
                .deadline(request.getDeadline())
                .user(getUser(email))
                .build();
        return toResponse(taskRepository.save(task));
    }

    public TaskResponse updateTask(String email, Long id, TaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setCategory(request.getCategory());
        task.setDeadline(request.getDeadline());
        return toResponse(taskRepository.save(task));
    }

    public TaskResponse markComplete(String email, Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus("COMPLETED");
        return toResponse(taskRepository.save(task));
    }

    public void deleteTask(String email, Long id) {
        taskRepository.deleteById(id);
    }
}