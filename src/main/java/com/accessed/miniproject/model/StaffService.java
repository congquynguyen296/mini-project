package com.accessed.miniproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "tbl_staff_service")
public class StaffService extends AbstractEntity<String> implements Serializable {
    private int exp;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;
}
