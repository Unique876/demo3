package com.example.controllers;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(length = 36)
    private String userId;
    
    @Column(nullable = false)
    private String fullName;
    
    @Column(nullable = false, unique = true, length = 10)
    private String mobNum;
    
    @Column(nullable = false, unique = true, length = 10)
    private String panNum;
    
    @Column(length = 36)
    private String managerId;
    
    @Column(nullable = false)
    private boolean isActive = true;
    
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private LocalDateTime updatedAt;

    // No-args constructor
    public User() {}

    // All-args constructor
    public User(String userId, String fullName, String mobNum, String panNum, 
                String managerId, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isActive) {
        this.userId = userId;
        this.fullName = fullName;
        this.mobNum = mobNum;
        this.panNum = panNum;
        this.managerId = managerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
    }
    public User(User user) {
        this.userId = user.getUserId();
        this.fullName = user.getFullName();
        this.mobNum = user.getMobNum();
        this.panNum = user.getPanNum();
        this.managerId = user.getManagerId();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.isActive = user.getIsActive();
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getMobNum() { return mobNum; }
    public void setMobNum(String mobNum) { this.mobNum = mobNum; }

    public String getPanNum() { return panNum; }
    public void setPanNum(String panNum) { this.panNum = panNum; }

    public String getManagerId() { return managerId; }
    public void setManagerId(String managerId) { this.managerId = managerId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
