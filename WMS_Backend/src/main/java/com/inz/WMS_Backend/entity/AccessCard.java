package com.inz.WMS_Backend.entity;

import com.inz.WMS_Backend.entity.dictionaries.AccessCardType;
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

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Basic
    @Column(name = "card_uid", nullable = false, length = 64, unique = true)
    private String cardUid;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "card_type_id", nullable = false)
    private AccessCardType type;

    @Basic
    private String description;
}
