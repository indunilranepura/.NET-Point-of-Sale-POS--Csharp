package com.pos.dto;

import com.pos.entity.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
    
    @NotBlank(message = "First name is required")
    @Size(max = 50)
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Size(max = 50)
    private String lastName;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100)
    private String password;
    
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;
    
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be less than 100")
    private Integer age;
    
    private User.Gender gender;
    
    @NotNull(message = "Role is required")
    private User.Role role;
    
    @DecimalMin(value = "0.0", message = "Salary must be positive")
    private Double salary;
    
    private LocalDate joinDate;
    private LocalDate birthDate;
    private String nid;
    
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number should be valid")
    private String phone;
    
    private String homeTown;
    private String currentCity;
    private String division;
    private String bloodGroup;
    private Integer postalCode;
}