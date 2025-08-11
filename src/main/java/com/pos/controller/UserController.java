package com.pos.controller;

import com.pos.dto.CreateUserDto;
import com.pos.dto.UserDto;
import com.pos.entity.User;
import com.pos.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class UserController {
    
    private final UserService userService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(required = false) String search) {
        List<UserDto> users = userService.searchUsers(search);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#id).userId == authentication.name")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/by-role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable User.Role role) {
        List<UserDto> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        UserDto createdUser = userService.createUser(createUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, 
                                            @Valid @RequestBody CreateUserDto updateUserDto) {
        UserDto updatedUser = userService.updateUser(id, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/stats/total")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getTotalUserCount() {
        Long count = userService.getTotalUserCount();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/stats/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getUserCountByRole(@PathVariable User.Role role) {
        Long count = userService.getUserCountByRole(role);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/stats/salary/total")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> getTotalSalary() {
        Double total = userService.getTotalSalary();
        return ResponseEntity.ok(total);
    }
    
    @GetMapping("/stats/salary/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> getSalaryByRole(@PathVariable User.Role role) {
        Double total = userService.getSalaryByRole(role);
        return ResponseEntity.ok(total);
    }
}