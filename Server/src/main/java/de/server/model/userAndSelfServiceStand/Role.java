package de.server.model.userAndSelfServiceStand;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Class represents the table "tb_role".
 */
@Setter
@Getter
@Table(name = "tb_role")
@Entity
public class Role {

    /**
     * ID of the user role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cl_role_nr", nullable = false)
    private Integer roleId;

    /**
     * Name of the user role.
     */
    @Lob
    @Column(name = "cl_role_name")
    private String roleName;

}