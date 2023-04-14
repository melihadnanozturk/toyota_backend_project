package com.mao.tytmistake.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tt_defect")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefectEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 6200740201106330923L;

    @Column(name = "defect_code", nullable = false)
    private String defectCode;

    @Column(name = "defect_desc")
    private String defectDesc;

    @OneToMany(mappedBy = "defect", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VehicleDefectEntity> vehicleDefectEntity;
}
