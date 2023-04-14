package com.mao.tytmistake.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tt_vehichle_defect")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDefectEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -6882968192497474298L;

    @Column(name = "vehicle_defect_desc")
    private String vehicleDefectDesc;

    @Column(name = "defect_image")
    private String defectImage;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private VehicleEntity vehicle;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "defect_id", referencedColumnName = "id")
    private DefectEntity defect;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "defectEntity")
    private List<DefectLocationEntity> defectLocation;
}
