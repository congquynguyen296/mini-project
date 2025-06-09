package com.accessed.miniproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "tbl_location")
public class Location extends AbstractEntity<String> implements Serializable {
    @Column(columnDefinition = "VARCHAR(1000)")
    private String image;
    private String country;
    private String city;
    private String district;
    private String street;

//    Truy vấn thủ công, không ánh xạ
    @Transient
    private List<Favorite> favoriteList;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location", cascade = CascadeType.ALL)
    private List<StaffLocation> staffLocationList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location", cascade = CascadeType.ALL)
    private List<ServiceLocation> serviceLocationList;
}
