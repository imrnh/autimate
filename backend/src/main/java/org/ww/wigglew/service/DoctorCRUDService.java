package org.ww.wigglew.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.ww.wigglew.entity.doctor.DoctorEntity;
import org.ww.wigglew.repo.DoctorsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorCRUDService {

    @Autowired
    private DoctorsRepository doctorsRepository;

    public ResponseEntity<?> createDoctor(DoctorEntity doctorEntity) {
        try {
            doctorsRepository.save(doctorEntity);
            return ResponseEntity.ok("Doctor created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating doctor: " + e.getMessage());
        }
    }

    public ResponseEntity<?> deleteDoctor(String id) {
        try {
            Optional<DoctorEntity> doctor = doctorsRepository.findById(id);
            if (doctor.isPresent()) {
                doctorsRepository.deleteById(id);
                return ResponseEntity.ok("Doctor deleted successfully");
            } else {
                return ResponseEntity.status(404).body("Doctor not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting doctor: " + e.getMessage());
        }
    }

    public ResponseEntity<?> updateDoctor(String id, DoctorEntity updatedDoctor) {
        try {
            Optional<DoctorEntity> doctor = doctorsRepository.findById(id);
            if (doctor.isPresent()) {
                updatedDoctor.setId(id);  // Ensure ID remains the same
                doctorsRepository.save(updatedDoctor);
                return ResponseEntity.ok("Doctor updated successfully");
            } else {
                return ResponseEntity.status(404).body("Doctor not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating doctor: " + e.getMessage());
        }
    }

    public ResponseEntity<?> getDoctor(String id) {
        try {
            Optional<DoctorEntity> doctor = doctorsRepository.findById(id);
            return doctor.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(404).body(null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching doctor: " + e.getMessage());
        }
    }

    public ResponseEntity<?> getAllDoctors() {
        try {
            List<DoctorEntity> doctors = doctorsRepository.findAll();
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching doctors: " + e.getMessage());
        }
    }
}

