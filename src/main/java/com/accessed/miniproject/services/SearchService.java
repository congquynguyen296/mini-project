package com.accessed.miniproject.services;


import com.accessed.miniproject.dto.response.ApiResponse;
import com.accessed.miniproject.dto.response.SearchResponse;
import com.accessed.miniproject.repositories.LocationRepository;
import com.accessed.miniproject.repositories.ServiceRepository;
import com.accessed.miniproject.repositories.StaffRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchService {

    LocationRepository locationRepository;
    ServiceRepository serviceRepository;
    StaffRepository staffRepository;

    public Map<String, Object> searchForUser(String keyword, String city) {

        Map<String, Object> result = new HashMap<>();

        List<SearchResponse> locations = locationRepository.searchBusinesses(keyword, city);
        List<SearchResponse> services = serviceRepository.searchServices(keyword, city);
        List<SearchResponse> staffs = staffRepository.searchStaffs(keyword, city);

        result.put("locations", locations);
        result.put("services", services);
        result.put("staffs", staffs);

        return result;
    }
}
