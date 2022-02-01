package de.server.request.userAndSelfServiceStand;

import lombok.Getter;
import lombok.Setter;

/**
 * This class contains all user entries for the User interface that are required to create, change and delete a user.
 */
@Setter
@Getter
public class NewUserRequest {

    /**
     * The name of the user.
     */
    private String newUserName;

    /**
     * The user password.
     */
    private String newUserPassword;

    /**
     * The user email.
     */
    private String newUserEmail;

    /**
     * The user role.
     */
    private String newUserRole;

    /**
     * Name of the self-service status to which the user is to be added.
     */
    private String addingSelfServiceStandName;

    /**
     * Name of the user who is adding a new user.
     */
    private String addingUser;
}
