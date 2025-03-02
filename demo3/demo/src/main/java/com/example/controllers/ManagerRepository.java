package com.example.controllers;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, String> {
    boolean existsByManagerIdAndIsActiveTrue(String managerId);
}