package com.thjavafest.wigglewonders.Wigglewonders.controller;//need changes

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.thjavafest.wigglewonders.Wigglewonders.entity.payload.LoginRequest;
import com.thjavafest.wigglewonders.Wigglewonders.entity.payload.MessageResponse;
import com.thjavafest.wigglewonders.Wigglewonders.entity.payload.SignupRequest;
import com.thjavafest.wigglewonders.Wigglewonders.entity.payload.UserInfoResponse;
import com.thjavafest.wigglewonders.Wigglewonders.repo.RoleRepository;
import com.thjavafest.wigglewonders.Wigglewonders.repo.UserRepository;
import com.thjavafest.wigglewonders.Wigglewonders.services.jwt.JwtUtils;
import com.thjavafest.wigglewonders.Wigglewonders.services.user.UserDetailsImpl;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thjavafest.wigglewonders.Wigglewonders.entity.user.RoleEntity;
import com.thjavafest.wigglewonders.Wigglewonders.entity.user.ERoleEntity;
import com.thjavafest.wigglewonders.Wigglewonders.entity.user.UserEntity;


@RestController
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/auth/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
    List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body(new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
  }

  @PostMapping("/auth/signup")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername()))
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));

    if (userRepository.existsByEmail(signUpRequest.getEmail()))
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));

    // Create new user's account
    UserEntity user = new UserEntity(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRoles();
    Set<RoleEntity> roles = new HashSet<>();

    if (strRoles == null) {
      RoleEntity userRole = roleRepository.findByName(ERoleEntity.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    }
    else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          RoleEntity adminRole = roleRepository.findByName(ERoleEntity.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "mod":
          RoleEntity modRole = roleRepository.findByName(ERoleEntity.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        default:
          RoleEntity userRole = roleRepository.findByName(ERoleEntity.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
