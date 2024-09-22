package com.inz.WMS_Backend.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "DICT_ADDRESS_TYPES")
public class AddressType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Basic
    @Column(name = "type", nullable = false, length = 64)
    private String name;
}
