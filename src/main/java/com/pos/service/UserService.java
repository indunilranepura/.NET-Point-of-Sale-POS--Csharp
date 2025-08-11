package com.pos.service;

import com.pos.dto.CreateUserDto;
import com.pos.dto.UserDto;
import com.pos.entity.User;
import com.pos.exception.ResourceNotFoundException;
import com.pos.exception.DuplicateResourceException;
import com.pos.mapper.UserMapper;
import com.pos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public List<UserDto> searchUsers(String searchKey) {
        if (searchKey == null || searchKey.trim().isEmpty()) {
            return getAllUsers();
        }
        return userRepository.findBySearchKey(searchKey.trim())
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toDto(user);
    }
    
    public UserDto getUserByUserId(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with userId: " + userId));
        return userMapper.toDto(user);
    }
    
    public UserDto createUser(CreateUserDto createUserDto) {
        // Check if user already exists
        if (userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new DuplicateResourceException("User already exists with email: " + createUserDto.getEmail());
        }
        
        User user = userMapper.toEntity(createUserDto);
        
        // Generate user ID
        user.setUserId(generateUserId(createUserDto.getRole()));
        
        // Encode password
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        
        // Set join date if not provided
        if (user.getJoinDate() == null) {
            user.setJoinDate(LocalDate.now());
        }
        
        User savedUser = userRepository.save(user);
        log.info("Created new user with ID: {}", savedUser.getUserId());
        
        return userMapper.toDto(savedUser);
    }
    
    public UserDto updateUser(Long id, CreateUserDto updateUserDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        // Check email uniqueness if changed
        if (!existingUser.getEmail().equals(updateUserDto.getEmail()) && 
            userRepository.existsByEmail(updateUserDto.getEmail())) {
            throw new DuplicateResourceException("User already exists with email: " + updateUserDto.getEmail());
        }
        
        // Update fields
        existingUser.setFirstName(updateUserDto.getFirstName());
        existingUser.setLastName(updateUserDto.getLastName());
        existingUser.setEmail(updateUserDto.getEmail());
        existingUser.setAge(updateUserDto.getAge());
        existingUser.setGender(updateUserDto.getGender());
        existingUser.setRole(updateUserDto.getRole());
        existingUser.setSalary(updateUserDto.getSalary());
        existingUser.setBirthDate(updateUserDto.getBirthDate());
        existingUser.setNid(updateUserDto.getNid());
        existingUser.setPhone(updateUserDto.getPhone());
        existingUser.setHomeTown(updateUserDto.getHomeTown());
        existingUser.setCurrentCity(updateUserDto.getCurrentCity());
        existingUser.setDivision(updateUserDto.getDivision());
        existingUser.setBloodGroup(updateUserDto.getBloodGroup());
        existingUser.setPostalCode(updateUserDto.getPostalCode());
        
        // Update password if provided
        if (updateUserDto.getPassword() != null && !updateUserDto.getPassword().trim().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
        }
        
        User savedUser = userRepository.save(existingUser);
        log.info("Updated user with ID: {}", savedUser.getUserId());
        
        return userMapper.toDto(savedUser);
    }
    
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        userRepository.delete(user);
        log.info("Deleted user with ID: {}", user.getUserId());
    }
    
    public List<UserDto> getUsersByRole(User.Role role) {
        return userRepository.findByRole(role)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
    
    // Dashboard statistics methods
    public Long getTotalUserCount() {
        return userRepository.countTotalUsers();
    }
    
    public Long getUserCountByRole(User.Role role) {
        return userRepository.countByRole(role);
    }
    
    public Double getTotalSalary() {
        return userRepository.sumTotalSalary();
    }
    
    public Double getSalaryByRole(User.Role role) {
        return userRepository.sumSalaryByRole(role);
    }
    
    private String generateUserId(User.Role role) {
        String prefix = switch (role) {
            case ADMIN -> "A";
            case CASHIER -> "C";
            case SALESMAN -> "S";
        };
        
        long count = userRepository.countByRole(role) + 1;
        return String.format("U-%s-%04d", prefix, count);
    }
}