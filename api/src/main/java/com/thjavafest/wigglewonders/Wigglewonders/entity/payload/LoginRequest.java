package com.thjavafest.wigglewonders.Wigglewonders.entity.payload;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
  @NotBlank
  private String username;

  @NotBlank
  private String email;

  @NotBlank
  private Collection<? extends GrantedAuthority> password;//should it be string?

  
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

  public Collection<? extends GrantedAuthority> getPassword() {
    return password;
  }

  public void setPassword(Collection<? extends GrantedAuthority> password) {
    this.password = password;
  }
}
