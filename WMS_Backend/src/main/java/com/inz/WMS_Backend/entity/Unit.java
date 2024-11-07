package com.inz.WMS_Backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
@Table(name = "UNITS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_unit_id")
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

    @Basic
    @Column(name="created_at", nullable = false, columnDefinition = "timestamp default current_timestamp")
    private Date createdAt;

    @OneToMany(mappedBy = "parentUnit", cascade = CascadeType.ALL)
    private Set<Unit> subunits;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    private Set<Position> positions;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "UNIT_MANAGER",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> managers;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "UNIT_SCHEDULE",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_id")
    )
    private Set<Schedule> schedules;
}
