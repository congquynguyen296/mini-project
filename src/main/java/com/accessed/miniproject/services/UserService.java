package com.accessed.miniproject.services;

import com.accessed.miniproject.dto.request.UserCreationRequest;
import com.accessed.miniproject.dto.response.UserCreationResponse;
import com.accessed.miniproject.enums.EErrorCode;
import com.accessed.miniproject.exception.AppException;
import com.accessed.miniproject.mapper.UserMapper;
import com.accessed.miniproject.model.User;
import com.accessed.miniproject.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserCreationResponse createUser(UserCreationRequest request) {

        // Check existed
        if (userRepository.findByEmail(request.getEmail()).isPresent())
            throw new AppException(EErrorCode.RESOURCE_EXISTED);

        if (userRepository.findByUsername(request.getUsername()).isPresent())
            throw new AppException(EErrorCode.RESOURCE_EXISTED);

        User newUser = userMapper.toUser(request);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        try {
            User savedUser = userRepository.save(newUser);
            log.info("SAVED USER: {}", savedUser);
            return UserCreationResponse.builder()
                    .username(savedUser.getUsername())
                    .password(savedUser.getPassword())
                    .build();
        } catch (RuntimeException e) {
            throw new AppException(EErrorCode.NOT_SAVE);
        }
    }
}
