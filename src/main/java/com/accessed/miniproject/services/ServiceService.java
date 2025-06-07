package com.accessed.miniproject.services;

import com.accessed.miniproject.dto.response.PopularServiceResponse;
import com.accessed.miniproject.repositories.ServiceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceService {

    ServiceRepository serviceRepository;
    RedisService redisService;

    @Cacheable(value = "popularServices", key = "#city")
    public List<PopularServiceResponse> findPopularServiceByCity(String city) {
        var result = serviceRepository.findPopularServiceByCity(city);
        return result;
    }

    @CacheEvict(value = "popularServices", key = "#city")
    public void removeCachePopularService(String city) {}

    public List<PopularServiceResponse> findPopularServiceByCityWithRedis(String city) {

        List cachedResult = redisService.getObject("popularServices::" + city, List.class);
        if (cachedResult != null) {
            return cachedResult;
        }

        List<PopularServiceResponse> result = serviceRepository.findPopularServiceByCity(city);

        redisService.setObject("popularServices::" + city, result, 3600);

        return result;
    }

    // Clear cache when data update
    public void clearPopularServicesCache(String city) {
        redisService.deleteObject("popularServices::" + city);
    }

}
