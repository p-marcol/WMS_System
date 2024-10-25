package com.inz.WMS_Backend.entity.dictionaries;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "DICT_POSITION_NAMES")
public class PositionName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Basic
    @Column(name = "name", nullable = false, length = 64)
    private String name;
}
