package com.inz.WMS_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Basic
    @Column(name = "start_at", nullable = false)
    private LocalDate startDate;

    @Basic
    @Column(name = "end_at")
    private LocalDate endDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private User createdBy;

    @OneToMany(mappedBy = "schedule")
    private Set<ScheduleBlock> scheduleBlocks;
}
