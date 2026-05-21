package com.flowdesk.service;

import com.flowdesk.dto.NoteRequest;
import com.flowdesk.dto.NoteResponse;
import com.flowdesk.model.Note;
import com.flowdesk.model.User;
import com.flowdesk.repository.NoteRepository;
import com.flowdesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private NoteResponse toResponse(Note n) {
        return new NoteResponse(n.getId(), n.getContent(), n.getCreatedAt());
    }

    public List<NoteResponse> getNotes(String email) {
        return noteRepository.findByUserIdOrderByCreatedAtDesc(getUser(email).getId())
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public NoteResponse create(String email, NoteRequest request) {
        Note note = Note.builder()
                .content(request.getContent())
                .user(getUser(email))
                .build();
        return toResponse(noteRepository.save(note));
    }

    public void delete(String email, Long id) {
        noteRepository.deleteById(id);
    }
}