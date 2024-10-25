package com.inz.WMS_Backend.entity;

import com.inz.WMS_Backend.entity.dictionaries.PositionName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "POSITIONS")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "name_id", nullable = false)
    private PositionName positionName;
}
