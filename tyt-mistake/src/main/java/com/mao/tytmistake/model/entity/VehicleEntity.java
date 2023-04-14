package com.mao.tytmistake.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tt_vehicle")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -8264618458387755650L;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "chassis_number")
    private String chassisNumber;

    @Column(name = "colour")
    private String colour;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VehicleDefectEntity> defect = new ArrayList<>();

}
