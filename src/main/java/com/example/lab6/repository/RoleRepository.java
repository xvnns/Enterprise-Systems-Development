package com.example.lab6.repository;

import com.example.lab6.entity.Role;
import com.example.lab6.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);
}
