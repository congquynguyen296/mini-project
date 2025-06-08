package com.accessed.miniproject.services;

import com.accessed.miniproject.dto.response.PopularLocationResponse;
import com.accessed.miniproject.repositories.LocationRepository;
import com.accessed.miniproject.repositories.ReviewRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationService {
    LocationRepository locationRepository;
    ReviewRepository reviewRepository;

    public int numberOfLocationsByCity(String city) {
        return locationRepository.countLocationByCity(city);
    }

    public List<PopularLocationResponse> getPopularLocations(String city) {
        return reviewRepository.popularLocations(city);
    }

}
