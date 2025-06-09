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
import java.text.Normalizer;
import java.util.Map;
import java.util.Objects;

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

            String res = state != null ? state : city;
            if (res == null)
                return "Không xác định";
            System.out.println(res);
            res = removePrefix(res);
            res = removeVietnameseTone(res);
            if (Objects.equals(res, "Thu Duc"))
                return "Ho Chi Minh";
            return res;
        }
        catch (Exception e){
            System.err.println("Lỗi khi gọi API: " + e.getMessage());
            return "Không xác định";
        }
    }
    public String removePrefix(String input) {
        return input
                .replaceFirst("Tỉnh ", "")
                .replaceFirst("Thành phố ", "");
    }

    public String removeVietnameseTone(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        // Loại bỏ các ký tự dấu
        str = str.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        // Thay các ký tự đặc biệt trong tiếng Việt
        str = str.replaceAll("đ", "d").replaceAll("Đ", "D");
        return str;
    }
}
