package com.mao.tytmistake.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.util.List;

@Entity
@Table(name = "tt_defect")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefectEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 798308213670575701L;

    @Column(name = "defect_code", nullable = false, unique = true)
    private String defectCode;

    @Column(name = "defect_desc")
    private String defectDesc;

    @OneToMany(mappedBy = "defect", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VehicleDefectEntity> vehicleDefectEntity;
}
