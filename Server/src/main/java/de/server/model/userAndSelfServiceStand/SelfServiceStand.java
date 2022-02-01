package de.server.model.userAndSelfServiceStand;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Class represents the table "tb_self_service_stand".
 */
@Getter
@Setter
@Table(name = "tb_self_service_stand")
@Entity
public class SelfServiceStand {

    /**
     * The ID of a self-service stand.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cl_self_service_stand_nr", nullable = false)
    private Integer selfServiceStandNId;

    /**
     * The name of the self-service stand.
     */
    @Column(name = "cl_self_service_stand_name", length = 100)
    private String selfServiceStandName;
}