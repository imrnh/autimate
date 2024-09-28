package org.ww.wigglew.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.ww.wigglew.entity.doctor.DoctorEntity;
import org.ww.wigglew.models.response.DoctorResponse;
import org.ww.wigglew.repo.DoctorsRepository;
import org.ww.wigglew.service.geocoding.IpGeolocation;

import java.io.File;
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

    public ResponseEntity<DoctorResponse> createDoctor(DoctorEntity doctorEntity, MultipartFile image) {
        try {
            // Handle file upload
            if (image != null && !image.isEmpty()) {
                String imageUrl = uploadImage(image); // Implement this method to upload the image to your server or cloud storage
                doctorEntity.setImage(imageUrl); // Set the image URL in the doctor entity
            }

            try {
                doctorEntity.setLatitude("0");
                doctorEntity.setLongitude("0");
                Map<String, String> locationData = getLocationData("Empire state building");
                if(locationData.get("error").isEmpty()) {
                    doctorEntity.setLatitude(locationData.get("latitude"));
                    doctorEntity.setLongitude(locationData.get("longitude"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Save doctor entity
            doctorsRepository.save(doctorEntity);
            return ResponseEntity.ok(positiveResponse("Doctor created successfully"));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(negativeResponse("Error creating doctor: " + e.getMessage()));
        }
    }

    private String uploadImage(MultipartFile image) throws IOException {
        String uploadDir = new File("src/main/resources/static/uploads").getAbsolutePath();

        // Create the directory if it doesn't exist
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Save the file to the static directory
        String filePath = uploadDir + "/" + image.getOriginalFilename();
        File destinationFile = new File(filePath);
        image.transferTo(destinationFile);

        return "/uploads/" + image.getOriginalFilename();  // Return the URL path for the file
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

    public ResponseEntity<?> updateDoctorOld(String id, DoctorEntity updatedDoctor) {
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

    public ResponseEntity<DoctorResponse> updateDoctor(String id, DoctorEntity updatedDoctor, MultipartFile image) {
        try {
            Optional<DoctorEntity> existingDoctorOpt = doctorsRepository.findById(id);
            if (existingDoctorOpt.isPresent()) {
                DoctorEntity existingDoctor = existingDoctorOpt.get();

                // Update fields from updatedDoctor
                existingDoctor.setName(updatedDoctor.getName());
                existingDoctor.setPhone(updatedDoctor.getPhone());
                existingDoctor.setDescription(updatedDoctor.getDescription());
                existingDoctor.setExperienceCount(updatedDoctor.getExperienceCount());
                existingDoctor.setGender(updatedDoctor.getGender());
                existingDoctor.setSpecialities(updatedDoctor.getSpecialities());
                existingDoctor.setOfficeHours(updatedDoctor.getOfficeHours());
                existingDoctor.setWebsite(updatedDoctor.getWebsite());
                existingDoctor.setAddress(updatedDoctor.getAddress());
                // Retain previous image if no new image is provided
                if (image != null && !image.isEmpty()) {
                    String imageUrl = uploadImage(image);
                    existingDoctor.setImage(imageUrl);
                }

                doctorsRepository.save(existingDoctor);
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
                if (geoResponse.get("error") != null && geoResponse.get("exception") != null) {
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

