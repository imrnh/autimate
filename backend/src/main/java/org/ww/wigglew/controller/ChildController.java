package org.ww.wigglew.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.ww.wigglew.entity.auth.Child;
import org.ww.wigglew.models.request.ChildRequest;
import org.ww.wigglew.models.response.AuthenticationResponse;
import org.ww.wigglew.service.ChildService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/child")
public class ChildController {

    @Autowired
    private ChildService childService;

    @PostMapping("/add")
    public ResponseEntity<AuthenticationResponse> addChild(
            @RequestHeader("Authorization") String jwtToken,
            @RequestBody ChildRequest childRequest)
    {
        AuthenticationResponse response = childService.addChild(jwtToken.substring(7),
                childRequest.getName(), childRequest.getDob());
        return ResponseEntity.ok(response);
    }


    @PostMapping("/toggle_active_session/{childId}")
    public ResponseEntity<AuthenticationResponse> toggleActiveSession(
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable String childId) {
        AuthenticationResponse response = childService.toggleActiveSession(jwtToken.substring(7), childId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Child>> listChildren(
            @RequestHeader("Authorization") String jwtToken) {

        // Call the child service to get the list of children
        List<Child> children = childService.getChildren(jwtToken.substring(7));
        return ResponseEntity.ok(children);
    }

    @DeleteMapping("/delete/{childId}")
    public ResponseEntity<String> deleteChild(
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable String childId) {

        childService.deleteChild(jwtToken.substring(7), childId);
        return ResponseEntity.ok("Child deleted successfully");
    }
}
