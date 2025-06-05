package com.accessed.miniproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "tbl_service_location")
public class ServiceLocation extends AbstractEntity<String> implements Serializable {
    private double price;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceLocation", cascade = CascadeType.ALL)
    private List<Appointment> appointmentList;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;
}
