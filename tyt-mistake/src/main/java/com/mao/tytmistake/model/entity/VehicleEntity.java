package com.mao.tytmistake.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tt_vehicle")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -3991441569726779565L;

    @Column(name = "model", nullable = false)
    @Enumerated(EnumType.STRING)
    private Model model;

    @Column(name = "chassis_number")
    private String chassisNumber;

    @Column(name = "colour")
    private Colour colour;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VehicleDefectEntity> defect = new ArrayList<>();

}
