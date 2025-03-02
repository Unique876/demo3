package com.example.controllers;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "managers")
public class Manager {
    @Id
    @Column(length = 36)
    private String managerId;
    
    @Column(nullable = false)
    private String fullName;
    
    @Column(nullable = false)
    private boolean isActive = true;

    // No-args constructor
    public Manager() {}

    // All-args constructor
    public Manager(String managerId, String fullName, Boolean isActive) {
        this.managerId = managerId;
        this.fullName = fullName;
        this.isActive = isActive;
    }

    // Getters and Setters
    public String getManagerId() { return managerId; }
    public void setManagerId(String managerId) { this.managerId = managerId; }

    public String getManagerName() { return fullName; }
    public void setManagerName(String managerName) { this.fullName = managerName; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
