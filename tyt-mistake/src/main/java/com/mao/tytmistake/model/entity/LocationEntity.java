package com.mao.tytmistake.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Entity class representing a location in the system.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tt_location")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1473266048998239512L;

    @Column(name = "y_location", nullable = false)
    private String yLocation;

    @Column(name = "x_location", nullable = false)
    private String xLocation;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "defectId", referencedColumnName = "id")
    private DefectEntity defectEntity;

}
