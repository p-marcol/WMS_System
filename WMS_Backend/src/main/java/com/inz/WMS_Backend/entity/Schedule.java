package com.inz.WMS_Backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
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

    @Basic
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Basic
    @Column(name = "start_at", nullable = false)
    private Date startDate;

    @Basic
    @Column(name = "end_at", nullable = false)
    private Date endDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private User createdBy;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private Set<ScheduleBlock> scheduleBlocks;
}
