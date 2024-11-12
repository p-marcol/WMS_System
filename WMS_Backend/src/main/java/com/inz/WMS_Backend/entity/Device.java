package com.inz.WMS_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Basic
    @Column(nullable = false)
    private String symbol;

    @Basic
    @Column(nullable = false)
    private String macAddress;

    @Basic
    @Column(nullable = false)
    private Date lastConnection;

    @Basic
    private String description;
}
