package org.ww.wigglew.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.ww.wigglew.entity.doctor.DoctorEntity;
import org.ww.wigglew.models.response.DoctorResponse;
import org.ww.wigglew.repo.DoctorsRepository;
import org.ww.wigglew.service.geocoding.IpGeolocation;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Comparator;

import static org.ww.wigglew.service.geocoding.ForwardGeoCoding.getLocationData;

@Service
public class DoctorManagerService {

    @Autowired
    private DoctorsRepository doctorsRepository;

    private DoctorResponse positiveResponse(String msg){
        return DoctorResponse.builder().responseStat(true).responseMessage(msg).build();
    }

    private DoctorResponse negativeResponse(String msg){
        return DoctorResponse.builder().responseStat(false).responseMessage(msg).build();
    }

    public ResponseEntity<DoctorResponse> createDoctor(DoctorEntity doctorEntity) {
        try {
            try { //different try catch block because we want doctor to be added even if long, lat not found.
                doctorEntity.setLatitude("0"); doctorEntity.setLongitude("0"); //start by setting these values to 0 to prevent further error.
                Map<String, String> locationData = getLocationData("Empire state building");
                if(locationData.get("error").isEmpty()) {
                    doctorEntity.setLatitude(locationData.get("latitude"));
                    doctorEntity.setLongitude(locationData.get("longitude"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //save doctor.
            doctorsRepository.save(doctorEntity);
            return ResponseEntity.ok(positiveResponse("Doctor created successfully"));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(negativeResponse("Error creating doctor: " + e.getMessage()));
        }
    }

    public ResponseEntity<DoctorResponse> deleteDoctor(String id) {
        try {
            Optional<DoctorEntity> doctor = doctorsRepository.findById(id);
            if (doctor.isPresent()) {
                doctorsRepository.deleteById(id);
                return ResponseEntity.ok(DoctorResponse.builder().responseStat(true).build());
            } else {
                return ResponseEntity.status(404).body(negativeResponse("Doctor not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(negativeResponse("Error deleting doctor: " + e.getMessage()));
        }
    }

    public ResponseEntity<?> updateDoctor(String id, DoctorEntity updatedDoctor) {
        try {
            Optional<DoctorEntity> doctor = doctorsRepository.findById(id);
            if (doctor.isPresent()) {
                updatedDoctor.setId(id);  // Ensure ID remains the same
                doctorsRepository.save(updatedDoctor);
                return ResponseEntity.ok(positiveResponse("Doctor updated successfully"));
            } else {
                return ResponseEntity.status(404).body(negativeResponse("Doctor not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(negativeResponse("Error updating doctor: " + e.getMessage()));
        }
    }

    public ResponseEntity<?> getDoctor(String id) {
        try {
            Optional<DoctorEntity> doctor = doctorsRepository.findById(id);
            return doctor.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(404).body(null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(negativeResponse("Error fetching doctor: " + e.getMessage()));
        }
    }

    public ResponseEntity<?> getAllDoctors(String ip) {
        try {
            List<DoctorEntity> doctors = doctorsRepository.findAll();

            // If IP address is not null, find lon, lat and sort the doctors.
            if (!ip.isEmpty()) {
                Map<String, String> geoResponse = IpGeolocation.getGeolocationFromIP(ip);
                if (geoResponse.get("error").isEmpty() && geoResponse.get("exception").isEmpty()) {
                    double userLongitude = Double.parseDouble(geoResponse.get("longitude"));
                    double userLatitude = Double.parseDouble(geoResponse.get("latitude"));

                    // Sort doctors by distance using the Haversine formula
                    doctors.sort(Comparator.comparingDouble(doctor -> IpGeolocation.calculateDistance(userLatitude, userLongitude,
                            Double.parseDouble(doctor.getLatitude()), Double.parseDouble(doctor.getLongitude()))));
                }
            }

            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(negativeResponse("Error fetching doctors: " + e.getMessage()));
        }
    }
}

