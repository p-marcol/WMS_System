package com.inz.WMS_Backend.entity;

import com.inz.WMS_Backend.entity.dictionaries.Authority;
import com.inz.WMS_Backend.entity.enums.eAuthority;
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
    @Column(nullable = false, length = 128)
    private String email;

    @Basic
    @Column(nullable = false, unique = true, length = 64)
    private String username;

    @Basic
    @Column(length = 255)
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

    @ManyToOne
    private Authority authority;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserPayoffData userPayoffData;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Position> positions;


    public String getAuthority() {
        return authority.getAuthority();
    }

    public Long getAuthorityId() {
        return authority.getId();
    }

    public Boolean isDeletable() {
        return lastPasswordResetDate == null;
    }

    public boolean isEditable() {
        return !isArchived;
    }

    public boolean isArchivable() {
        return !getAuthority().toUpperCase().equals(eAuthority.NEW_USER.name());
    }
}
