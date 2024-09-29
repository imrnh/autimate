package org.ww.wigglew.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ww.wigglew.config.jwt.JWTExtractorService;
import org.ww.wigglew.entity.auth.Child;
import org.ww.wigglew.entity.auth.UserEntity;
import org.ww.wigglew.models.response.AuthenticationResponse;
import org.ww.wigglew.repo.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ChildService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTExtractorService jwtExtractorService;

    public AuthenticationResponse addChild(String jwtToken, String childName, LocalDate dob) {
        String phone = jwtExtractorService.extractUsername(jwtToken);
        UserEntity user = userRepository.findByPhone(phone).orElseThrow(() -> new RuntimeException("User not found"));

        Child newChild = Child.builder()
                .id(UUID.randomUUID().toString())
                .name(childName)
                .dob(dob)
                .activeSession(false) // default to false
                .build();

        user.getChildren().add(newChild);
        userRepository.save(user);

        return AuthenticationResponse.builder()
                .requestSuccess(true)
                .requestMessage("Child added successfully")
                .build();
    }

    public AuthenticationResponse toggleActiveSession(String jwtToken, String childId) {
        String phone = jwtExtractorService.extractUsername(jwtToken);
        UserEntity user = userRepository.findByPhone(phone).orElseThrow(() -> new RuntimeException("User not found"));

        // Ensure only one child has an active session at a time
        for (Child child : user.getChildren()) {
            child.setActiveSession(child.getId().equals(childId));
        }

        userRepository.save(user);

        return AuthenticationResponse.builder()
                .requestSuccess(true)
                .requestMessage("Active session toggled")
                .build();
    }

    public List<Child> getChildren(String jwtToken) {
        String phone = jwtExtractorService.extractUsername(jwtToken);
        UserEntity user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getChildren();
    }

    public void deleteChild(String jwtToken, String childId) {
        String phone = jwtExtractorService.extractUsername(jwtToken);
        UserEntity user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getChildren().removeIf(child -> child.getId().equals(childId));
        userRepository.save(user);
    }
}
