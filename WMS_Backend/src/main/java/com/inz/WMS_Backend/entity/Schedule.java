package com.inz.WMS_Backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "SCHEDULES")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Unit unit;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @Basic
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDate createdAt;

    @Basic
    @Column(name = "start_at", nullable = false)
    private LocalDate startDate;

    @Basic
    @Column(name = "end_at")
    private LocalDate endDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private User createdBy;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private Set<ScheduleBlock> scheduleBlocks;
}
