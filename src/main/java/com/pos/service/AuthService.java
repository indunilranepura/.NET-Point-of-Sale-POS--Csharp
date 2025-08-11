package com.pos.service;

import com.pos.dto.LoginRequest;
import com.pos.dto.LoginResponse;
import com.pos.dto.UserDto;
import com.pos.entity.User;
import com.pos.exception.InvalidCredentialsException;
import com.pos.mapper.UserMapper;
import com.pos.repository.UserRepository;
import com.pos.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUserId(),
                            loginRequest.getPassword()
                    )
            );
            
            User user = (User) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(user);
            UserDto userDto = userMapper.toDto(user);
            
            log.info("User {} logged in successfully", user.getUserId());
            return new LoginResponse(token, userDto);
            
        } catch (AuthenticationException e) {
            log.warn("Failed login attempt for user: {}", loginRequest.getUserId());
            throw new InvalidCredentialsException("Invalid user ID or password");
        }
    }
    
    public UserDto getCurrentUser(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));
        return userMapper.toDto(user);
    }
}