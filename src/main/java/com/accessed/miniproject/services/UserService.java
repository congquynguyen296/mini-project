package com.accessed.miniproject.services;

import com.accessed.miniproject.dto.request.UserCreationRequest;
import com.accessed.miniproject.dto.response.UserCreationResponse;
import com.accessed.miniproject.dto.response.UserResponse;
import com.accessed.miniproject.enums.EErrorCode;
import com.accessed.miniproject.exception.AppException;
import com.accessed.miniproject.mapper.UserMapper;
import com.accessed.miniproject.model.User;
import com.accessed.miniproject.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public Page<User> findUsersByCity(String city, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        return userRepository.findByCity(city, pageRequest);
    }

    /* ================================ Cache ================================ */

    @Cacheable(value = "users", key = "#key + ':' + #page + ':' + #size")
    public List<UserResponse> searchUsersByCity(String key, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findByCity(key.toLowerCase(), pageRequest);

        List<UserResponse> result = userPage.getContent().stream()
                .map(user -> UserResponse.builder()
                        .city(user.getCity())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .build())
                .collect(Collectors.toList());

        return result;
    }


    @CacheEvict(value = "users", allEntries = true)
    public void evictUsersCache() {

    }
}
