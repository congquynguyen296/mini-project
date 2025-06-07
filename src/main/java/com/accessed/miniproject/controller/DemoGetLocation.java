package com.accessed.miniproject.controller;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("api/v1/geo")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DemoGetLocation {
    @GetMapping({"/{ipAddress}"})
    public String getLocation(@PathVariable("ipAddress") String ipAddress,
                              HttpServletRequest req
    ) throws IOException, GeoIp2Exception {
        String ipa = req.getHeader("X-FORWARDED-FOR");
        if (ipa == null || ipa.isEmpty()) {
            ipa = req.getRemoteAddr();
        }
        System.out.println(ipa);

//        File database = new File("/Users/mac/BINH/accessed/mini-project/ipinfo_lite.mmdb");
//        DatabaseReader reader = new DatabaseReader.Builder(database).build();
//        InetAddress ip = InetAddress.getByName(ipa);
//
//        // Replace "city" with the appropriate method for your database, e.g.,
//        // "country".
//        CityResponse response = reader.city(ip);
//
//        Country country = response.getCountry();
//        System.out.println(country.getIsoCode());            // 'US'
//        System.out.println(country.getName());               // 'United States'
//        System.out.println(country.getNames().get("zh-CN")); // '美国'
//
//        Subdivision subdivision = response.getMostSpecificSubdivision();
//        System.out.println(subdivision.getName());    // 'Minnesota'
//        System.out.println(subdivision.getIsoCode()); // 'MN'
//
//        City city = response.getCity();
//        System.out.println(city.getName()); // 'Minneapolis'
//
//        Postal postal = response.getPostal();
//        System.out.println(postal.getCode()); // '55455'
//
//        Location location = response.getLocation();
//        System.out.println(location.getLatitude());  // 44.9733
//        System.out.println(location.getLongitude()); // -93.2323
        return "Hello World";
    }
}
