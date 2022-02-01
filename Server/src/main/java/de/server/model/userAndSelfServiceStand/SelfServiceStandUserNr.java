package de.server.model.userAndSelfServiceStand;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Class represents the table "tb_self_service_stand".
 */
@Table(name = "tb_self_service_stand_user_nr", indexes = {
        @Index(name = "cl_role_nr", columnList = "cl_role_nr"),
        @Index(name = "cl_user_nr", columnList = "cl_user_nr"),
        @Index(name = "cl_self_service_stand_nr", columnList = "cl_self_service_stand_nr")
})
@Setter
@Getter
@Entity
public class SelfServiceStandUserNr {
    /**
     * An ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cl_self_service_stand_nr_id", nullable = false)
    private Integer id;

    /**
     * The ID of a self-service stand.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "cl_self_service_stand_nr", nullable = false)
    private SelfServiceStand selfServiceStandNr;

    /**
     * The ID of a user.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "cl_user_nr", nullable = false)
    private User userNr;

    /**
     * The ID of a role.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "cl_role_nr", nullable = false)
    private Role clRoleNr;
}