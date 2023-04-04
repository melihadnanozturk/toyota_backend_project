package com.mao.tytmistake.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tt_defect_location")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DefectLocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "y_location", nullable = false)
    private String yLocation;

    @Column(name = "x_location", nullable = false)
    private String xLocation;

    //todo: burası kontrol edilebilir.
    @OneToOne(mappedBy = "defectLocation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private VehicleDefectEntity defectEntity;

}
