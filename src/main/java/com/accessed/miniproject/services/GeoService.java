package com.accessed.miniproject.services;

import com.accessed.miniproject.repositories.StaffRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GeoService {
    RestTemplate restTemplate;
    String NOMINATIM_URL = "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=%f&lon=%f";
    public String getCity(double lat, double lng) {
        String url = String.format(NOMINATIM_URL, lat, lng);
        RestTemplate restTemplate = new RestTemplate();

        try{
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            Map<String, Object> body = response.getBody();

            if (body == null || !body.containsKey("address")) {
                return "Không xác định";
            }

            Map<String, Object> address = (Map<String, Object>) body.get("address");
            String state = (String) address.get("state");
            String city = (String) address.get("city");

            return state != null ? state : (city != null ? city : "Không xác định");
        }
        catch (Exception e){
            System.err.println("Lỗi khi gọi API: " + e.getMessage());
            return "Không xác định";
        }
    }
}
