package com.mao.tytmistake.model.entity;

import com.mao.tytmistake.model.entity.enums.Defect;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tt_vehichle_defect")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDefectEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -1411462554817294988L;

    @Enumerated(EnumType.STRING)
    private Defect defect;

    @Column(name = "vehicle_defect_desc")
    private String vehicleDefectDesc;

    @Column(name = "defect_image")
    private String defectImage;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private VehicleEntity vehicle;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "vehicleDefectEntity")
    private List<DefectLocationEntity> defectLocation;
}
