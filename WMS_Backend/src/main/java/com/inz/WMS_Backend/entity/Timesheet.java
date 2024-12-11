package com.inz.WMS_Backend.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "TIMESHEETS")
public class Timesheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Basic
    @Column(nullable = false, length = 256)
    private String name;

    @Basic
    @Column(nullable = false)
    private LocalDate date;

    @Basic
    @Column(nullable = false)
    private double hours;

    @Basic
    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean approved;

    @Basic
    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean rejected;

    @Basic
    private LocalDate approvedDate;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;
}
