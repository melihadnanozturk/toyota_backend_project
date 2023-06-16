package com.mao.tytmistake.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tt_location")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -3773257122505753461L;

    @Column(name = "y_location", nullable = false)
    private String yLocation;

    @Column(name = "x_location", nullable = false)
    private String xLocation;

    //todo: burası kontrol edilebilir.
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "defectId", referencedColumnName = "id")
    private DefectEntity defectEntity;

}
