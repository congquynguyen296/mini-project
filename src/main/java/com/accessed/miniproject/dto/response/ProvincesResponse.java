package com.accessed.miniproject.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProvincesResponse {

    String city;
    String image;
    String cityCode;
    String country;
}
