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
@Table(name = "tbl_favorite")
public class Favorite extends AbstractEntity<String> implements Serializable {
    @Enumerated(EnumType.STRING)
    private ERateType subjectType;
    private String subjectId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
