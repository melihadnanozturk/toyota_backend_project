package com.mao.tytmistake.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "tt_vehicle")
@Data
public class VehicleEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "chassis_number")
    private String chassisNumber;

    @Column(name = "colour")
    private String colour;
}
