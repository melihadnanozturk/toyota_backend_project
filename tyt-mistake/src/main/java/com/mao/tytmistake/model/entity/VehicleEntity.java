package com.mao.tytmistake.model.entity;

import com.mao.tytmistake.model.entity.enums.Colour;
import com.mao.tytmistake.model.entity.enums.Model;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
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
    private static final long serialVersionUID = 2220055646868781446L;

    @Column(name = "model", nullable = false)
    @Enumerated(EnumType.STRING)
    private Model model;

    @Column(name = "chassis_number")
    private String chassisNumber;

    @Column(name = "colour")
    @Enumerated(EnumType.STRING)
    private Colour colour;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DefectEntity> defect;

}
