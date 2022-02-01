package de.server.service.userAndSelfServiceStand;

import de.server.response.userAndSelfServiceStand.NameSelfServiceStandResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SelfServiceStandService {

    /**
     * Creates a new self-service stand.
     *
     * @param selfServiceStandName self-service stand name
     * @return self-service stand id
     */
    int createSelfServiceStand(String selfServiceStandName);


    /**
     * Method returns the self-service-stand number.
     *
     * @param selfServiceStandName The self-service-stand name.
     * @return The self-service-stand number.
     */
    int getSelfServiceStandNr(String selfServiceStandName);

    /**
     * This method creates a connection between an existing user and an existing self-service stand.
     *
     * @param userName           The user who is being added.
     * @param selfServiceStandNr The self-service-stand nr.
     * @param roleName           The role Name.
     */
    void createNewShortcut(String userName, int selfServiceStandNr, String roleName);

    /**
     * Removes a user from an existing self-service stand.
     *
     * @param removeUserName       The name of the user to be removed.
     * @param selfServiceStandName The self-service-stand name.
     */
    void removeUserEntry(String removeUserName, String selfServiceStandName);

    /**
     * This method creates a new self-service booth and adds the user.
     *
     * @param selfServiceStandName The self-servie-stand name.
     * @param userName             The user name.
     */
    void createSelfServiceStandEntry(String selfServiceStandName, String userName);

    /**
     * Return a List with self-Service-Stand
     *
     * @param userName the user name
     * @return list
     */
    List<NameSelfServiceStandResponse> getListOfSelfServiceStand(String userName);
}
