package com.inz.WMS_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Table(name = "USER_PAYOFF_DATA")
public class UserPayoffData {
    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Basic
    @Column(nullable = false, length = 11)
    private String SWIFT;

    @Basic
    @Column(nullable = false, length = 34)
    private String IBAN;

}
