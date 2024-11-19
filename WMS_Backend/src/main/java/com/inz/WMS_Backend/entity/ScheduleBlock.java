package com.inz.WMS_Backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Entity
@Getter
@Setter
@Table(name = "SCHEDULE_BLOCKS")
public class ScheduleBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Basic
    @Column(name = "start_day", nullable = false)
    private Short startDay;

    @Basic
    @Column(name = "end_day", nullable = false)
    private Short endDay;

    @Basic
    @Column(name = "start_hour", nullable = false)
    private Time startHour;

    @Basic
    @Column(name = "end_hour", nullable = false)
    private Time endHour;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;
}
