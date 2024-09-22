package com.inz.WMS_Backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
@Table(name = "ADRESSES")
public class Adress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Basic
    @Column(nullable = false, length = 64)
    private String street;

    @Basic
    @Column(name = "street_number", nullable = false)
    private Integer streetNumber;

    @Basic
    private Integer floor;

    @Basic
    private Integer apartmentNumber;

    @Basic
    @Column(nullable = false, length = 6)
    private String postcode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_type_id", nullable = false)
    private AddressType addressType;

}
