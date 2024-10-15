package com.inz.WMS_Backend.entity;

import com.inz.WMS_Backend.entity.dictionaries.Authority;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
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
    @Column(nullable = false, unique = true, length = 128)
    private String email;

    @Basic
    @Column(nullable = false, unique = true, length = 64)
    private String username;

    @Basic
    @Column(nullable = false, length = 255)
    private String password;

    @Basic
    @Column(name = "first_name", length = 64)
    private String firstName;

    @Basic
    @Column(name = "last_name", length = 64)
    private String lastName;

    @Basic
    @Column(name = "date_of_birth")
    private Date birthdate;

    @Basic
    @Column(length = 15)
    private String phone;

    @Basic
    @Column(name = "is_archived", nullable = false, columnDefinition = "boolean default false")
    private boolean isArchived;

    @ManyToOne
    private User creator;

    @Basic
    @Column(name = "last_password_reset_date")
    private Date lastPasswordResetDate;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authorities;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserPayoffData userPayoffData;
}
