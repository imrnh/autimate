package com.thjavafest.wigglewonders.Wigglewonders.entity.payload;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.*;

public class SignupRequest {
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;


  @NotBlank
  @Size(max = 15)
  @Pattern(regexp = "^\\d{11,14}$", message = "Phone number must be 11 or 14 digits")
  private String phone;

  @NotBlank
  @Pattern(regexp = "^\\d{10,17}$", message = "NID must be between 10 or 17 digits")
  private String nid;

  @NotBlank
  //@Size(max = 50)
//  @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/\\d{4}$",
////          message = "Date of birth must be in the format dd/mm/yyyy")
  private LocalDate dob;

  private Set<String> roles;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getNid() {
    return nid;
  }

  public void setNid(String nid) {
    this.nid = nid;
  }

  public LocalDate getDob() {
    return dob;
  }

  public void setDob(LocalDate dob) {
    this.dob = dob;
  }
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<String> getRoles() {
    return this.roles;
  }

  public void setRole(Set<String> roles) {
    this.roles = roles;
  }
}
