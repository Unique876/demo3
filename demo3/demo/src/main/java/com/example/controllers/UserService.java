package com.example.controllers;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManagerRepository managerRepository;

    public User createUser(User user) {

        if (!user.getPanNum().matches("[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}")) {
            throw new IllegalArgumentException("Invalid PAN number");
        }
        System.out.println("updated");
        user.setPanNum(user.getPanNum().toUpperCase());
        
        if (!user.getMobNum().matches("^\\+?91?0?[6789]\\d{9}$")) {
            throw new IllegalArgumentException("Invalid mobile number");
        }
        user.setMobNum(user.getMobNum().replaceAll("[^0-9]", "").substring(user.getMobNum().length() - 10));

        if (user.getManagerId() != null && !managerRepository.existsByManagerIdAndIsActiveTrue(user.getManagerId())) {
            throw new IllegalArgumentException("Invalid manager ID");
        }

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public ResponseEntity<?> updateUser(Map<String, Object> requestData) {
        if (!requestData.containsKey("user_ids") || !requestData.containsKey("update_data")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing required keys: user_ids or update_data"));
        }

        List<String> userIds = (List<String>) requestData.get("user_ids");
        Map<String, Object> updateData = (Map<String, Object>) requestData.get("update_data");

        // Ensure at least one user ID is provided
        if (userIds.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "user_ids list cannot be empty."));
        }

        // Check if all user IDs exist
        List<User> users = userRepository.findAllById(userIds);
        if (users.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "No valid users found for provided user_ids."));
        }

        // Validation for bulk update (Only manager_id allowed)
        if (userIds.size() > 1 && (updateData.containsKey("full_name") || updateData.containsKey("mob_num") || updateData.containsKey("pan_num"))) {
            return ResponseEntity.badRequest().body(Map.of("error", "Bulk update is only allowed for manager_id. Other fields must be updated individually."));
        }

        // Validate and update users
        for (User user : users) {
            if (updateData.containsKey("full_name")) {
                String fullName = (String) updateData.get("full_name");
                if (fullName.isBlank()) {
                    return ResponseEntity.badRequest().body(Map.of("error", "Full name cannot be empty."));
                }
                user.setFullName(fullName);
            }

            if (updateData.containsKey("mob_num")) {
                String mobileNumber = (String) updateData.get("mob_num");
                mobileNumber = formatMobileNumber(mobileNumber);
                user.setMobNum(mobileNumber);
            }

            if (updateData.containsKey("pan_num")) {
                String panNum = ((String) updateData.get("pan_num")).toUpperCase();
                if (!isValidPANNumber(panNum)) {
                    return ResponseEntity.badRequest().body(Map.of("error", "Invalid PAN number format."));
                }
                user.setPanNum(panNum);
            }

            if (updateData.containsKey("manager_id")) {
                String managerId = (String) updateData.get("manager_id");

                // Check if manager exists
                if (!managerRepository.existsById(managerId)) {
                    return ResponseEntity.badRequest().body(Map.of("error", "Invalid manager ID."));
                }

                // If user already has a manager, mark old entry as inactive and create new entry
                if (user.getManagerId() != null) {
                    user.setIsActive(false);
                    userRepository.save(user);

                    User newUserEntry = new User(user);
                    newUserEntry.setUserId(UUID.randomUUID().toString());
                    newUserEntry.setManagerId(managerId);
                    newUserEntry.setIsActive(true);
                    newUserEntry.setUpdatedAt(LocalDateTime.now());
                    userRepository.save(newUserEntry);
                } else {
                    user.setManagerId(managerId);
                    user.setUpdatedAt(LocalDateTime.now());
                    userRepository.save(user);
                }
            } else {
                user.setUpdatedAt(LocalDateTime.now());
                userRepository.save(user);
            }
        }

        return ResponseEntity.ok(Map.of("message", "User(s) updated successfully."));
    }

    private String formatMobileNumber(String mobNum) {
        return mobNum.replace("+91", "").replace("0", "").trim();
    }

    private boolean isValidPANNumber(String panNum) {
        return panNum.matches("[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}");
    }
}
