package com.accessed.miniproject.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "tbl_staff")
public class Staff extends AbstractEntity<String> implements Serializable {
    private String fullName;
    private String image;
    private String address;
    private String phone;

    //    Truy vấn thủ công, không ánh xạ
    @Transient
    private List<Favorite> favoriteList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "staff", cascade = CascadeType.ALL)
    private List<StaffService> staffServiceList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "staff", cascade = CascadeType.ALL)
    private List<StaffLocation> staffLocationList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "staff", cascade = CascadeType.ALL)
    private List<Appointment> appointmentList;

}
