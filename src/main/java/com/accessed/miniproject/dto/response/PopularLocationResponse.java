package com.accessed.miniproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PopularLocationResponse {
    private String locationId;
    private String district;
    private String street;
    private String name;
    private String image;
    private double avgRate;
    private long appointmentCount;
    private long favoriteCount;
}
