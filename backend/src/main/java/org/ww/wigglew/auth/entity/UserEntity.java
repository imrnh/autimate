package org.ww.wigglew.auth.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


/**
 * Spring security needs a user.
 * We wanted to make sure our user is considered as user for Spring Security framework.
 * Hence, implemented the UserDetails interface.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Document(collection = "users")
public class UserEntity implements UserDetails {
    @Id
    private String id;
    private String fullName;
    private String phone;
    private String password;

    @Enumerated(EnumType.STRING)
    private AccessRole role;

    @Enumerated(EnumType.STRING)
    private PhoneNumberVerificationStatus verificationStatus;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //we only allow 1 role per user. Hence, SimpleGrantedAuthority
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //if false, cannot access account.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //if false, cannot access account.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
