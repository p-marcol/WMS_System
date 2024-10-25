package com.inz.WMS_Backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
@Table(name = "UNITS")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_unit_id", nullable = false)
    private Unit parentUnit;

    @Basic
    @Column(nullable = false, length = 64)
    private String name;

    @Basic
    @Column(nullable = false, length = 128)
    private String description;

    @Basic
    @Column(name="work_ended", nullable = false, columnDefinition = "boolean default false")
    private boolean workEnded;


    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    private Set<Position> positions;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "UNIT_SCHEDULE",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_id")
    )
    private Set<Schedule> schedules;
}
