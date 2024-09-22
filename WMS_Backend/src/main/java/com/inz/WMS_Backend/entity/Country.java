package com.inz.WMS_Backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
@Table(name = "COUNTRIES")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Basic
    @Column(nullable = false, length = 64)
    private String name;

    @Basic
    @Column(length = 2)
    private String alpha2Code;

    @Basic
    @Column(length = 3)
    private String alpha3Code;

    @Basic
    @Column(length = 3)
    private String numericCode;
}
