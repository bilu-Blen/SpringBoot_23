package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<AppRole, Long> {
    AppRole findByAppRole(String role);
}
