package com.inz.WMS_Backend.entity;

import com.inz.WMS_Backend.entity.dictionaries.PositionName;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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

    @Basic
    @Column(name = "start_at", nullable = false)
    private LocalDate startDate;

    @Basic
    @Column(name = "end_at")
    private LocalDate endDate;

    public boolean isManager() {
        return positionName.getName().equals("MANAGER");
    }
}
