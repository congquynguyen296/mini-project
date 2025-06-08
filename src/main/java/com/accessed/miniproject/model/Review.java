package com.accessed.miniproject.model;


import com.accessed.miniproject.enums.ERateType;
import jakarta.persistence.*;
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
@Table(name = "tbl_review")
public class Review extends AbstractEntity<String> implements Serializable {
    private String content;
    private int rate;
    private String subjectId;

    @Enumerated(EnumType.STRING)
    private ERateType subjectType;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
}