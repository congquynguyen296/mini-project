package com.accessed.miniproject.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

// DTO cache
public class UserResponse implements Serializable {

    String username;
    String email;
    String city;
}
