package com.accessed.miniproject.services;

import com.accessed.miniproject.dto.request.UserCreationRequest;
import com.accessed.miniproject.dto.response.PopularLocationResponse;
import com.accessed.miniproject.dto.response.PopularStaffResponse;
import com.accessed.miniproject.dto.response.UserCreationResponse;
import com.accessed.miniproject.dto.response.UserResponse;
import com.accessed.miniproject.enums.EErrorCode;
import com.accessed.miniproject.exception.AppException;
import com.accessed.miniproject.mapper.UserMapper;
import com.accessed.miniproject.model.User;
import com.accessed.miniproject.repositories.StaffRepository;
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
public class StaffService {
    StaffRepository staffRepository;

    public long count(String city){
        // return staffRepository.count();
        return staffRepository.countAllStaffByCity(city);
    }

    public List<PopularStaffResponse> getPopularStaffs(String city) {
        return staffRepository.popularStaff(city);
    }
}
