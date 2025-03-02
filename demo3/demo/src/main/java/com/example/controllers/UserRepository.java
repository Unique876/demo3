package com.example.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByMobNum(String mobNum);
    List<User> findByManagerId(String managerId);
}
