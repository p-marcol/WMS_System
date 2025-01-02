package com.inz.WMS_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@Getter @Setter
@Builder
@Table(name = "USER_ACCESS")
public class UserAccess {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Device device;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private AccessCard accessCard;

    @Basic
    @Column(name = "access_granted", nullable = false, columnDefinition = "boolean default false")
    private Boolean accessGranted;

    @CreationTimestamp
    @Column(name = "accessed_at", nullable = false)
    private Timestamp accessedAt;

    public UserAccess() {
        this.accessGranted = false;
    }
}
