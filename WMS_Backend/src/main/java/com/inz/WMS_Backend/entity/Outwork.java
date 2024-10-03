package com.inz.WMS_Backend.entity;

import com.inz.WMS_Backend.entity.dictionaries.WorkType;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
@Table(name = "OUTWORKS")
public class Outwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Basic
    @Column(nullable = false)
    private Date date_from;

    @Basic
    @Column(nullable = false)
    private Date date_to;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "work_type_id", nullable = false)
    private WorkType workType;

    @Basic
    @Column(nullable = false)
    private String location;
}
