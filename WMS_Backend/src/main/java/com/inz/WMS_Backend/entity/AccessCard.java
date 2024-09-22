package com.inz.WMS_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Table(name = "ACCESS_CARDS")
public class AccessCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Basic
    @Column(name = "card_serial", nullable = false, length = 64)
    private String cardSerial;

    @Basic
    @Column(name = "card_hash", nullable = false, length = 64)
    private String cardHash;

}
