package com.mao.tytmistake.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tt_defect")
@Data
public class DefectEntity {

    //todo: POstgre de sequance olduğundan burayı kontrol et.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "defect_code", nullable = false)
    private String defectCode;

    @Column(name = "defect_desc")
    private String defectDesc;
}
