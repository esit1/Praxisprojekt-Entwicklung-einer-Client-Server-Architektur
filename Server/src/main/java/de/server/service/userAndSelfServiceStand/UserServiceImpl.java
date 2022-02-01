package de.server.service.userAndSelfServiceStand;

import de.server.model.userAndSelfServiceStand.SelfServiceStandUserNr;
import de.server.model.userAndSelfServiceStand.User;
import de.server.repository.userAndSelfServiceStand.RoleRepository;
import de.server.repository.userAndSelfServiceStand.SelfServiceStandRepository;
import de.server.repository.userAndSelfServiceStand.SelfServiceStandUserNrRepository;
import de.server.repository.userAndSelfServiceStand.UserRepository;
import de.server.request.userAndSelfServiceStand.UserRequest;
import net.minidev.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class contains methods that are used to manage the user.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * Administrator role
     */
    private static final String ADMINROLE = "Administrator";

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * userRepository to user information.
     */
    private final UserRepository userRepository;

    /**
     * userRepository to selfServiceStandUser information.
     */
    private final SelfServiceStandRepository selfServiceStandRepository;

    /**
     * userRepository to role information.
     */
    private final RoleRepository roleRepository;

    /**
     * userRepository to selfServiceStandUserNr information.
     */
    private final SelfServiceStandUserNrRepository selfServiceStandUserNrRepository;

    /**
     * Service provides the self-service-stand logic.
     */
    private final SelfServiceStandService selfServiceStandService;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, SelfServiceStandRepository selfServiceStandRepository, RoleRepository roleRepository, SelfServiceStandUserNrRepository selfServiceStandUserNrRepository, SelfServiceStandService selfServiceStandService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.selfServiceStandRepository = selfServiceStandRepository;
        this.roleRepository = roleRepository;
        this.selfServiceStandUserNrRepository = selfServiceStandUserNrRepository;
        this.selfServiceStandService = selfServiceStandService;
    }


    /**
     * Method changes the user data.
     *
     * @param user private String userName;
     *             private String userPassword;
     *             private String selfServiceStandName;
     *             private String email;
     *             private String changeUserName;
     * @return user
     */
    @Override
    public User changeUserData(UserRequest user) {

        User changeUser = new User();
        changeUser.setUserId(userRepository.findByUserName(user.getUserName()).getUserId());

        if (!user.getUserName().isEmpty())
            changeUser.setUserName(user.getChangeUserName());

        if (!user.getUserPassword().isEmpty())
            changeUser.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));

        if (!user.getEmail().isEmpty())
            changeUser.setUserEmail(user.getEmail());

        userRepository.save(changeUser);

        return changeUser;
    }

    /**
     * Connections self-service stand, the roles and the connections between the tables User and Self-Service stand are set.
     *
     * @param userName           The user name.
     * @param selfServiceStandID The self-Service-stand id.
     * @param roleName           The role name.
     */
    @Override
    public void setConnectionsSelfServiceStand(String userName, int selfServiceStandID, String roleName) {

        SelfServiceStandUserNr entry = new SelfServiceStandUserNr();
        entry.setUserNr(userRepository.findByUserName(userName));
        entry.setSelfServiceStandNr(selfServiceStandRepository.findBySelfServiceStandNId(selfServiceStandID));
        entry.setClRoleNr(roleRepository.findByRoleName(roleName));

        selfServiceStandUserNrRepository.save(entry);
    }

    /**
     * Create a new User.
     *
     * @param userName username
     * @param password password
     * @param email    email
     * @return new user
     */
    @Override
    public User createUser(String userName, String password, String email) {
        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setUserPassword(bCryptPasswordEncoder.encode(password));
        newUser.setUserEmail(email);

        userRepository.save(newUser);
        return newUser;
    }

    /**
     * Checks whether the email meets the requirements.
     *
     * @param email email
     * @return Does the email meet the requirements ?(true or false)
     */
    @Override
    public boolean checkEmailCorrectly(String email) {

        final String PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";

        return email.matches(PATTERN);
    }

    /**
     * Checks whether the password meets the requirements.
     *
     * @param password password
     * @return Does the password meet the requirements ?(true or false)
     */
    @Override
    public boolean checkPasswordCorrectly(String password) {

        /*
       The word must contain at least one number.
       The word must contain at least one lowercase letter.
       The word must contain at least one capital letter.
       The word cannot contain any spaces.
       The word must contain at least 6 characters.
       */
        final String PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}";

        return password.matches(PATTERN);
    }

    /**
     * This method provides a message why the password is not correct.
     * The massage includes the reason why the password is incorrect.
     *
     * @param password User password
     * @return Message with the reason why the password is incorrect.
     */
    @Override
    public JSONObject messageIncorrectPassword(String password) {

        //create new json object
        JSONObject message = new JSONObject();

        String errorMessage = "Invalid password: ";

        //Password does not contain a number.
        if (!password.matches(".*[0-9].*")) {
            errorMessage = errorMessage + "no number, ";
        }

        //Password does not contain lowercase letters.
        if (!password.matches("(.*[a-z])")) {
            errorMessage = errorMessage + "no lower case letter, ";
        }

        //Password does not contain capital letters.
        if (!password.matches("(.*[A-Z])")) {
            errorMessage = errorMessage + "no capital letter, ";
        }

        //Password contains spaces.
        if (!password.matches("(\\S+$)")) {
            errorMessage = errorMessage + "contains spaces, ";
        }

        //Password does not contain 6 characters.
        if (!password.matches(".{6,}")) {
            errorMessage = errorMessage + "no 6 characters, ";
        }

        //Deletes the last two characters.
        errorMessage = errorMessage.substring(0, errorMessage.length() - 2);

        message.put("Message", errorMessage);

        return message;
    }

    /**
     * It is checked whether the user is admin.
     *
     * @param userName             The user name.
     * @param selfServiceStandName The self-service-stand name.
     * @return true or false
     */
    @Override
    public boolean checkUserAdmin(String userName, String selfServiceStandName) {
        return selfServiceStandUserNrRepository.existsBySelfServiceStandNr_SelfServiceStandNIdAndClRoleNr_RoleNameAndUserNr_UserName(selfServiceStandService.getSelfServiceStandNr(selfServiceStandName), ADMINROLE, userName);
    }

    /**
     * This method changes the role of a user.
     *
     * @param selfServiceStandName The self-service-stand name.
     * @param newRole              The new role name.
     * @param user                 The user name.
     */
    @Override
    public void changeRole(String selfServiceStandName, String newRole, String user) {

        SelfServiceStandUserNr selfServiceStandUserNrEntry = new SelfServiceStandUserNr();

        selfServiceStandUserNrEntry.setId(selfServiceStandUserNrRepository.findBySelfServiceStandNr_SelfServiceStandNameAndUserNr_UserName(selfServiceStandName, user).getId());
        selfServiceStandUserNrEntry.setClRoleNr(roleRepository.findByRoleName(newRole));

        selfServiceStandUserNrRepository.save(selfServiceStandUserNrEntry);
    }

    /**
     * Returns a list with user.
     *
     * @param selfServiceStandName The self-service-stand name.
     * @return list with users.
     */
    @Override
    public List<User> getListUser(String selfServiceStandName) {

        int selfServiceStandNr = selfServiceStandService.getSelfServiceStandNr(selfServiceStandName);

        //  List<User> listWithUser = null;
        List<User> listWithUser = new ArrayList<>();

        for (SelfServiceStandUserNr entry : selfServiceStandUserNrRepository.findBySelfServiceStandNr_SelfServiceStandNId(selfServiceStandNr)) {

            listWithUser.add(userRepository.findByUserId(entry.getId()));
        }
        return listWithUser;
    }

}
