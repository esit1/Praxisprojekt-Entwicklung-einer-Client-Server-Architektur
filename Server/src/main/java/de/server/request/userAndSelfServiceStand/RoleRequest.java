package de.server.request.userAndSelfServiceStand;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {

    /**
     * Username from which the role is to be changed.
     */
    String changeUserName;

    /**
     * The user who changes the role.
     */
    String userName;

    /**
     * The self-service-stand name.
     */
    String selfServiceStandName;

    /**
     * The new role name.
     */
    String newRole;
}
