package com.accessed.miniproject.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    String fullName;
    String email;
    String username;
    String password;
    LocalDate dob;
    String country;
    String city;
    String district;
    String street;
    String phone;
}
