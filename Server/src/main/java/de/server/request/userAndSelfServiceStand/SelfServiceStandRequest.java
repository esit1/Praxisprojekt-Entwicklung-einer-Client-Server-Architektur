package de.server.request.userAndSelfServiceStand;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelfServiceStandRequest {

    /**
     * User to be added.
     */
    String addingUserName;

    /**
     * User who adds a user.
     */
    String userName;

    /**
     * The self-service-stand name.
     */
    String selfServiceStandName;
}
