package com.flowdesk.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "habits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String habitName;
    @Builder.Default
    private int streak = 0;
    @Builder.Default
    private boolean completedToday = false;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}