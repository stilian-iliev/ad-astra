package com.adastra.models;

import com.adastra.models.enumerations.UserRoleEnum;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum name;

    public UserRole() {
    }

    public UserRole(UserRoleEnum name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public UserRoleEnum getName() {
        return name;
    }
}
