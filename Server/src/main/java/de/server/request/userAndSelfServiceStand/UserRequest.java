package de.server.request.userAndSelfServiceStand;

import lombok.Getter;
import lombok.Setter;

/**
 * This class contains all user entries for the User interface that are required to create, change and delete a user.
 */
@Getter
@Setter
public class UserRequest {

    /**
     * The name of the user.
     */
    private String userName;

    /**
     * The usewr password.
     */
    private String userPassword;

    /**
     * The name of the self-service stand name.
     */
    private String selfServiceStandName;

    /**
     * The user email.
     */
    private String email;

    /**
     * The changed name (new name).
     */
    private String changeUserName;
}
