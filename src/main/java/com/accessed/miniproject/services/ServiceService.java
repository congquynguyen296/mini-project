package com.accessed.miniproject.services;

import com.accessed.miniproject.dto.response.PopularServiceResponse;
import com.accessed.miniproject.repositories.ServiceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceService {

    ServiceRepository serviceRepository;

    public List<PopularServiceResponse> findPopularServiceByCity(String city) {
        var result = serviceRepository.findPopularServiceByCity(city);

        return result;
    }

}
