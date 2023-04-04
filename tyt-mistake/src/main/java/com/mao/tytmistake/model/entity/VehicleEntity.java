package com.mao.tytmistake.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tt_vehicle")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "chassis_number")
    private String chassisNumber;

    @Column(name = "colour")
    private String colour;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VehicleDefectEntity> defect = new ArrayList<>();

}
