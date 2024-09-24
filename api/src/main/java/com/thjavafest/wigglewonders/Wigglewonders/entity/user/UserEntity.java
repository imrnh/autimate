package com.thjavafest.wigglewonders.Wigglewonders.entity.user;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "users")
public class UserEntity {
  @Id
  private String id;//needs to use sequence generator to auto create id...might do later

  @NotBlank
  @Size(max = 40)
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
  ////message = "Date of birth must be in the format dd/mm/yyyy")
  private LocalDate dob;

  @NotBlank
  @Size(max = 120)
  private String password;

  @DBRef
  private Set<RoleEntity> roles = new HashSet<>();

  public UserEntity() {
  }

  public UserEntity(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
public UserEntity(String username, String email, String phone, String nid, LocalDate dob, String encode) {
    this.username = username;
    this.email = email;
    this.phone=phone;
    this.nid=nid;
    this.dob=dob;
    this.password = encode;
  }//this constructor is for signup purpose....pkease take a look whether it could break password privacy
//
//
//    public void setId(String id) {
//    this.id = id;
//  }
//
//    public void setUsername(String username) {
//    this.username = username;
//  }
//
//    public void setEmail(String email) {
//    this.email = email;
//  }
//
//    public void setPhone(String phone) {
//    this.phone = phone;
//  }
//
//    public void setNid(String nid) {
//    this.nid = nid;
//  }
//
//    public void setDob(LocalDate dob) {
//    this.dob = dob;
//  }
//
//    public void setPassword(String password) {
//    this.password = password;
//  }
//
//    public void setRoles(Set<RoleEntity> roles) {
//    this.roles = roles;
//  }

}
