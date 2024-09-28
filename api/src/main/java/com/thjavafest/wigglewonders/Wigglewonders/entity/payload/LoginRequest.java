package com.thjavafest.wigglewonders.Wigglewonders.entity.payload;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Setter
@Getter
public class LoginRequest {
  @NotBlank
  private String username;

  @NotBlank
  private String email;

  @NotBlank
  private String password;
}
