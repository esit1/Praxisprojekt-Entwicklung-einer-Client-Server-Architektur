package de.server.service.userAndSelfServiceStand;

import de.server.model.userAndSelfServiceStand.User;
import de.server.request.userAndSelfServiceStand.UserRequest;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

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
    User changeUserData(UserRequest user);

    /**
     * Connections self-service stand, the roles and the connections between the tables User and Self-Service stand are set.
     *
     * @param userName           username
     * @param selfServiceStandID self-Service-stand id
     * @param roleName           role name
     */
    void setConnectionsSelfServiceStand(String userName, int selfServiceStandID, String roleName);


    /**
     * Create a new User.
     *
     * @param userName username
     * @param password password
     * @param email    email
     * @return new user
     */
    User createUser(String userName, String password, String email);

    /**
     * Checks whether the email meets the requirements.
     *
     * @param email email
     * @return Does the email meet the requirements ?(true or false)
     */
    boolean checkEmailCorrectly(String email);

    /**
     * Checks whether the password meets the requirements.
     *
     * @param password password
     * @return Does the password meet the requirements ?(true or false)
     */
    boolean checkPasswordCorrectly(String password);

    /**
     * This method provides a message why the password is not correct.
     * The massage includes the reason why the password is incorrect.
     *
     * @param password User password
     * @return Message with the reason why the password is incorrect.
     */
    JSONObject messageIncorrectPassword(String password);

    /**
     * It is checked whether the user is admin.
     *
     * @param userName             The user name.
     * @param selfServiceStandName The self-service-stand name.
     * @return true or false
     */
    boolean checkUserAdmin(String userName, String selfServiceStandName);

    /**
     * This method changes the role of a user.
     *
     * @param selfServiceStandName The self-service-stand name.
     * @param newRole              The new role name.
     * @param user                 The user name.
     */
    void changeRole(String selfServiceStandName, String newRole, String user);

    /**
     * Returns a list with user.
     *
     * @param selfServiceStandName The self-service-stand name.
     * @return list with users.
     */
    List<User> getListUser(String selfServiceStandName);
}
