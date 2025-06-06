package com.accessed.miniproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "tbl_appointment")
public class Appointment extends AbstractEntity<String> implements Serializable {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;
    @ManyToOne
    @JoinColumn(name = "service_location_id")
    private ServiceLocation serviceLocation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appointment", cascade = CascadeType.ALL)
    private List<Review> reviewList;
}
