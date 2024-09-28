package org.ww.wigglew.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.ww.wigglew.entity.doctor.DoctorEntity;
import org.ww.wigglew.service.DoctorManagerService;
import org.ww.wigglew.service.BucketStorageService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/doctor/")
public class DoctorController {

    @Autowired
    private DoctorManagerService doctorManagerService;

    @Autowired
    private BucketStorageService bucketStorageService;

    private boolean validateAuthority(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails)
            return !userDetails.getAuthorities().toString().equals("[ADMIN]");
        return true;
    }

    @GetMapping("/url/presigned")
    public String fetchPreSignedUrl() throws Exception {
        return bucketStorageService.getPreSignedUrl("jpg");
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDoctor(
            @RequestPart("doctorData") DoctorEntity doctorEntity,
            @RequestPart("image") MultipartFile image) {

        if (validateAuthority()) return ResponseEntity.status(403).body("Forbidden");

        return doctorManagerService.createDoctor(doctorEntity, image);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable String id) {
        if (validateAuthority()) return ResponseEntity.status(403).body("Forbidden");
        return doctorManagerService.deleteDoctor(id);
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> updateDoctor(@PathVariable String id, @RequestBody DoctorEntity doctorEntity) {
//        if (validateAuthority()) return ResponseEntity.status(403).body("Forbidden");
//        return doctorManagerService.updateDoctor(id, doctorEntity);
//    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDoctor(
            @PathVariable String id,
            @RequestPart("doctorData") DoctorEntity doctorEntity,
            @RequestPart(value = "image", required = false) MultipartFile image) {  // Image is optional
        if (validateAuthority()) return ResponseEntity.status(403).body("Forbidden");
        return doctorManagerService.updateDoctor(id, doctorEntity, image);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getDoctor(@PathVariable String id) {
        //if (validateAuthority()) return ResponseEntity.status(403).body("Forbidden"); // anyone can get doctor including users.
        return doctorManagerService.getDoctor(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllDoctors(@RequestHeader("X-IP-Address") String ip) {
        return doctorManagerService.getAllDoctors(ip);
    }
}
