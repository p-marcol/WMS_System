package com.inz.WMS_Backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Basic
    @Column(nullable = false, unique = true, length = 64)
    private String username;

    @Basic
    @Column(nullable = false, length = 255)
    private String password;

    @Basic
    @Column(nullable = false, unique = true, length = 128)
    private String email;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID")
    )
    private Set<Authority> authorities;
}
