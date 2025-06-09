package com.accessed.miniproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PopularStaffResponse {
    private String staffid;
    private String fullname;
    private String address;
    private String image;
    private double avgRate;
    private long appointmentCount;
    private long favoriteCount;
}
