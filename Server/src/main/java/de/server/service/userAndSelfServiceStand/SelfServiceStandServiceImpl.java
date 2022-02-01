package de.server.service.userAndSelfServiceStand;

import de.server.model.userAndSelfServiceStand.SelfServiceStand;
import de.server.model.userAndSelfServiceStand.SelfServiceStandUserNr;
import de.server.repository.userAndSelfServiceStand.RoleRepository;
import de.server.repository.userAndSelfServiceStand.SelfServiceStandRepository;
import de.server.repository.userAndSelfServiceStand.SelfServiceStandUserNrRepository;
import de.server.repository.userAndSelfServiceStand.UserRepository;
import de.server.response.userAndSelfServiceStand.NameSelfServiceStandResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class contains methods that are used to manage the self-service-stand information.
 */
@Service
public class SelfServiceStandServiceImpl implements SelfServiceStandService {

    /**
     * Administrator role
     */
    private static final String ADMINROLE = "Administrator";

    /**
     * SelfServiceStandUserNrRepository to selfServiceStandUserNr information.
     */
    private final SelfServiceStandUserNrRepository selfServiceStandUserNrRepository;

    /**
     * UserRepository to user information.
     */
    private final UserRepository userRepository;

    /**
     * SelfServiceStandRepository to selfServiceStand information.
     */
    private final SelfServiceStandRepository selfServiceStandRepository;

    /**
     * userRepository to role information.
     */
    private final RoleRepository roleRepository;

    public SelfServiceStandServiceImpl(SelfServiceStandUserNrRepository selfServiceStandUserNrRepository, UserRepository userRepository, SelfServiceStandRepository selfServiceStandRepository, RoleRepository roleRepository) {
        this.selfServiceStandUserNrRepository = selfServiceStandUserNrRepository;
        this.userRepository = userRepository;
        this.selfServiceStandRepository = selfServiceStandRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Creates a new self-service stand.
     *
     * @param selfServiceStandName self-service stand name
     * @return self-service stand id
     */
    @Override
    public int createSelfServiceStand(String selfServiceStandName) {

        SelfServiceStand newSelfServiceStand = new SelfServiceStand();
        newSelfServiceStand.setSelfServiceStandName(selfServiceStandName);

        //save new entry
        SelfServiceStand entry = selfServiceStandRepository.save(newSelfServiceStand);

        return entry.getSelfServiceStandNId();
    }


    /**
     * Method returns the self-service-stand number.
     *
     * @param selfServiceStandName The self-service-stand name.
     * @return The self-service-stand number.
     */
    @Override
    public int getSelfServiceStandNr(String selfServiceStandName) {
        return selfServiceStandRepository.findBySelfServiceStandName(selfServiceStandName).getSelfServiceStandNId();
    }

    /**
     * This method creates a connection between an existing user and an existing self-service stand.
     *
     * @param userName           The user who is being added.
     * @param selfServiceStandNr The self-service-stand nr.
     * @param roleName           The role Name.
     */
    public void createNewShortcut(String userName, int selfServiceStandNr, String roleName) {

        SelfServiceStandUserNr entry = new SelfServiceStandUserNr();

        entry.setClRoleNr(roleRepository.findByRoleName(roleName));
        entry.setUserNr(userRepository.findByUserName(userName));
        entry.setSelfServiceStandNr(selfServiceStandRepository.findBySelfServiceStandNId(selfServiceStandNr));

        selfServiceStandUserNrRepository.save(entry);
    }

    /**
     * Removes a user from an existing self-service stand.
     *
     * @param removeUserName       The name of the user to be removed.
     * @param selfServiceStandName The self-service-stand name.
     */
    @Override
    public void removeUserEntry(String removeUserName, String selfServiceStandName) {

        SelfServiceStandUserNr removeEntry = new SelfServiceStandUserNr();

        removeEntry.setId(selfServiceStandUserNrRepository.findBySelfServiceStandNr_SelfServiceStandNameAndUserNr_UserName(selfServiceStandName, removeUserName).getId());

        selfServiceStandUserNrRepository.delete(removeEntry);
    }

    /**
     * This method creates a new self-service booth and adds the user.
     *
     * @param selfServiceStandName The self-servie-stand name.
     * @param userName             The user name.
     */
    @Override
    public void createSelfServiceStandEntry(String selfServiceStandName, String userName) {

        //Create new self-service-stand and save die nr
        int selfServiceStandNr = createSelfServiceStand(selfServiceStandName);

        createNewShortcut(userName, selfServiceStandNr, ADMINROLE);
    }

    /**
     * Return a List with self-Service-Stand
     *
     * @param userName the user name
     * @return list
     */
    @Override
    public List<NameSelfServiceStandResponse> getListOfSelfServiceStand(String userName) {

        //new empty List
        List<NameSelfServiceStandResponse> listWithName = new ArrayList<>();

        //add self-Service-Stand name
        for (SelfServiceStandUserNr entry : selfServiceStandUserNrRepository.findByUserNr_UserName(userName)) {
            NameSelfServiceStandResponse name = new NameSelfServiceStandResponse();
            name.setSelfServiceStandName(selfServiceStandRepository.findBySelfServiceStandNId(entry.getSelfServiceStandNr().getSelfServiceStandNId()).getSelfServiceStandName());

            listWithName.add(name);

        }

        return listWithName;
    }
}
