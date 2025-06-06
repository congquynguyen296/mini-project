package com.accessed.miniproject.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "tbl_user")
public class User extends AbstractEntity<String> implements Serializable {
    private String fullName;
    private String email;
    private String username;
    private String password;
    private LocalDate dob;
    private String country;
    private String city;
    private String district;
    private String street;
    private String phone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Appointment> appointmentList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviewList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Favorite> favoriteList;
}
