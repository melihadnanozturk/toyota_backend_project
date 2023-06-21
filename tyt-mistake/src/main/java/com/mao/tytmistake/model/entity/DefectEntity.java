package com.mao.tytmistake.model.entity;

import com.mao.tytmistake.model.entity.enums.Defect;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Entity class representing a defect in the system.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tt_defect")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefectEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -236835569387242391L;

    @Enumerated(EnumType.STRING)
    private Defect defect;

    @Column(name = "defect_desc")
    private String defectDesc;

    @Column(name = "image_data")
    private byte[] defectImage;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private VehicleEntity vehicle;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "defectEntity")
    private List<LocationEntity> defectLocation;
}
