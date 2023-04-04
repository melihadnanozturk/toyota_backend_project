package com.mao.tytmistake.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tt_vehichle")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDefectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "defect_image")
    private String defectImage;

    @Column(name = "vehicle_defect_desc")
    private String vehicleDefectDesc;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private VehicleEntity vehicle;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "defect_id", referencedColumnName = "id")
    private DefectEntity defect;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private DefectLocationEntity defectLocation;
}
