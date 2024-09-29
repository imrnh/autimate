package org.ww.wigglew.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.ww.wigglew.entity.auth.Child;
import org.ww.wigglew.models.request.ChildRequest;
import org.ww.wigglew.models.response.AuthenticationResponse;
import org.ww.wigglew.service.CallService;
import org.ww.wigglew.service.ChildService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/call")
public class CallController {

    @Autowired
    private CallService callService;

    @PostMapping("/new_room/{roomID}")
    public ResponseEntity<?> addCall(@PathVariable String roomID)
    {
        try {
            callService.addCall(roomID);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete_room/{roomID}")
    public ResponseEntity<?> deleteCall(@PathVariable String roomID)
    {
        try {
            callService.deleteCall(roomID);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
