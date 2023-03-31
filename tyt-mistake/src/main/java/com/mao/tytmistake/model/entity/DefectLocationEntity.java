package com.mao.tytmistake.model.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tt_defect_location")
@Data
public class DefectLocationEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "vehicle_defect_id", referencedColumnName = "id")
    private VehicleDefectEntity defectEntity;

    @Column(name = "y_location")
    private String yLocation;

    @Column(name = "x_location")
    private String xLocation;


}
