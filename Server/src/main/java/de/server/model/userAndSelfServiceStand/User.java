package de.server.model.userAndSelfServiceStand;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Class represents the table "tb_user".
 */
@Setter
@Getter
@Table(name = "tb_user")
@Entity
public class User {
    /**
     * The user ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cl_user_nr", nullable = false)
    private Integer userId;

    /**
     * The name of the user.
     */
    @Column(name = "cl_user_name")
    private String userName;

    /**
     * The user's password.
     */
    @Column(name = "cl_user_password")
    private String userPassword;

    /**
     * The user's email address.
     */
    @Column(name = "cl_user_email", nullable = false, length = 30)
    private String userEmail;

}