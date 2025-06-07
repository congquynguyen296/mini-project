package com.accessed.miniproject.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PopularServiceResponse {

    String id;
    String name;
    String image;
    Long appointmentCount;
}
