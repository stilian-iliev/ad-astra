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
    private UserRoleEnum roleName;

    public long getId() {
        return id;
    }

    public UserRoleEnum getRoleName() {
        return roleName;
    }
}
