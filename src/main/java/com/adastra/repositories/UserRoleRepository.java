package com.adastra.repositories;

import com.adastra.models.UserRole;
import com.adastra.models.enumerations.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    UserRole findByName(UserRoleEnum user);

    boolean existsByName(UserRoleEnum name);
}
