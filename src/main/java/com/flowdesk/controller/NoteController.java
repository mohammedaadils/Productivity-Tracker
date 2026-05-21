package com.flowdesk.controller;

import com.flowdesk.dto.NoteRequest;
import com.flowdesk.dto.NoteResponse;
import com.flowdesk.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getNotes(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(noteService.getNotes(userDetails.getUsername()));
    }

    @PostMapping
    public ResponseEntity<NoteResponse> create(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestBody NoteRequest request) {
        return ResponseEntity.ok(noteService.create(userDetails.getUsername(), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetails userDetails,
                                       @PathVariable Long id) {
        noteService.delete(userDetails.getUsername(), id);
        return ResponseEntity.noContent().build();
    }
}