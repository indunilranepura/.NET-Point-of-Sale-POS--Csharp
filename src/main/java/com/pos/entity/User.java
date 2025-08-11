package com.pos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", unique = true, nullable = false)
    private String userId;
    
    @NotBlank(message = "First name is required")
    @Size(max = 50)
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Size(max = 50)
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100)
    @Column(name = "password", nullable = false)
    private String password;
    
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be less than 100")
    @Column(name = "age")
    private Integer age;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    
    @DecimalMin(value = "0.0", message = "Salary must be positive")
    @Column(name = "salary")
    private Double salary;
    
    @Column(name = "join_date")
    private LocalDate joinDate;
    
    @Column(name = "birth_date")
    private LocalDate birthDate;
    
    @Column(name = "nid", unique = true)
    private String nid;
    
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number should be valid")
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "home_town")
    private String homeTown;
    
    @Column(name = "current_city")
    private String currentCity;
    
    @Column(name = "division")
    private String division;
    
    @Column(name = "blood_group")
    private String bloodGroup;
    
    @Column(name = "postal_code")
    private Integer postalCode;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    // UserDetails implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
    
    @Override
    public String getUsername() {
        return userId;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return isActive;
    }
    
    public enum Gender {
        MALE, FEMALE, OTHER
    }
    
    public enum Role {
        ADMIN, CASHIER, SALESMAN
    }
}