package com.inz.WMS_Backend.entity.dictionaries;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "DICT_WORK_TYPES")
public class WorkType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Basic
    @Column(name = "work_type", nullable = false, length = 64)
    private String name;
}
