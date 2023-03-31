package com.mao.tytmistake.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tt_vehicle")
@Data
public class VehicleDefectEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "defect_image")
    private String defectImage;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private VehicleEntity vehicleEntity;

    @ManyToOne
    @JoinColumn(name = "defect_id", referencedColumnName = "id")
    private DefectEntity defectEntity;

}
