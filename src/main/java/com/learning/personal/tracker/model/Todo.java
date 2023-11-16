package com.learning.personal.tracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "todo_list")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id", columnDefinition = "serial")
    private Long todoId;
    @Column(name = "todo_name")
    private String todoName;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "completed_date")
    private LocalDateTime completedDate;
    @Column(name = "deadline_date")
    private LocalDateTime deadlineDate;
    @Column(name = "todo_reminder")
    private Integer todoReminder;
    @Column(name = "todo_priority")
    private Integer todoPriority;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @Transient
    private Long userId;
}
