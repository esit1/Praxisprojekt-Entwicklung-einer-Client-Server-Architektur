package de.server.controller.userAndSelfServiceStand;


import de.server.model.userAndSelfServiceStand.User;
import de.server.repository.userAndSelfServiceStand.SelfServiceStandRepository;
import de.server.repository.userAndSelfServiceStand.SelfServiceStandUserNrRepository;
import de.server.repository.userAndSelfServiceStand.UserRepository;
import de.server.request.userAndSelfServiceStand.NewUserRequest;
import de.server.request.userAndSelfServiceStand.UserRequest;
import de.server.response.userAndSelfServiceStand.userResponse;
import de.server.service.userAndSelfServiceStand.SelfServiceStandService;
import de.server.service.userAndSelfServiceStand.UserService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * With the help of this class, new users can be created and changed.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * Administrator role number
     */
    private static final String ADMINROLE = "Administrator";

    /**
     * Logger of the GoodsNameController class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * Service provides the user logic.
     */
    private final UserService userService;

    /**
     * Service provides the self-service-stand logic.
     */
    private final SelfServiceStandService selfServiceStandService;

    /**
     * SelfServiceStandUserNrRepository to self-service-stand information.
     */
    private final SelfServiceStandUserNrRepository selfServiceStandUserNrRepository;

    /**
     * SelfServiceStandUserNrRepository to self-service-stand information.
     */
    private final SelfServiceStandRepository selfServiceStandRepository;

    /**
     * userRepository to user information.
     */
    private final UserRepository userRepository;

    /**
     *
     * @param userService userService
     * @param selfServiceStandService selfServiceStandService
     * @param selfServiceStandUserNrRepository selfServiceStandUserNrRepository
     * @param selfServiceStandRepository selfServiceStandRepository
     * @param userRepository userRepository
     */
    public UserController(UserService userService, SelfServiceStandService selfServiceStandService, SelfServiceStandUserNrRepository selfServiceStandUserNrRepository, SelfServiceStandRepository selfServiceStandRepository, UserRepository userRepository) {
        this.userService = userService;
        this.selfServiceStandService = selfServiceStandService;
        this.selfServiceStandUserNrRepository = selfServiceStandUserNrRepository;
        this.selfServiceStandRepository = selfServiceStandRepository;
        this.userRepository = userRepository;
    }

    /**
     * A new admin is created and a new self-service stand is created. A new user can only be created if there is not yet a user with the name.
     * The password and the email address are checked for correctness.
     *
     * @param newUser private String userName;
     *                private String userPassword;
     *                private String selfServiceStandName;
     *                private String email;
     * @return message, httpStatus or new user
     */
    @PostMapping("/newAdmin")
    public ResponseEntity<?> newUserAdmin(@RequestBody UserRequest newUser) {

        //It will remove excess spaces.
        newUser.setUserName(newUser.getUserName().trim());
        newUser.setSelfServiceStandName(newUser.getSelfServiceStandName().trim());

        //create new json object
        JSONObject message = new JSONObject();

        //It is checked whether username  has not yet been assigned.
        if (!userRepository.existsByUserName(newUser.getUserName())) {

            //It is checked whether the self-service-stand name has not yet been assigned.
            if (!selfServiceStandRepository.existsBySelfServiceStandName(newUser.getSelfServiceStandName())) {

                //Checks whether the password meets the requirements.
                if (userService.checkPasswordCorrectly(newUser.getUserPassword())) {

                    //check email correctly
                    if (userService.checkEmailCorrectly(newUser.getEmail())) {

                        //create new User
                        User user = userService.createUser(newUser.getUserName(), newUser.getUserPassword(), newUser.getEmail());

                        //create new selfServiceStand
                        int selfServiceStandID = selfServiceStandService.createSelfServiceStand(newUser.getSelfServiceStandName());

                        //Connections self-service stand
                        userService.setConnectionsSelfServiceStand(newUser.getUserName(), selfServiceStandID, ADMINROLE);

                        LOGGER.info("User " + newUser.getUserName() + "created.");
                        LOGGER.info("Self-service stand " + newUser.getSelfServiceStandName() + "created.");

                        //the user response. (name, email, self-service-stand name)
                        userResponse response = new userResponse();
                        response.setUserName(user.getUserName());
                        response.setSelfServiceStandName(newUser.getSelfServiceStandName());
                        response.setEmail(user.getUserEmail());

                        return new ResponseEntity<>(response, HttpStatus.CREATED);

                    } else {
                        LOGGER.info("The email " + newUser.getEmail() + " address you entered is incorrect.");
                        message.put("Message", "The email " + newUser.getEmail() + " address you entered is incorrect.");

                        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
                    }

                } else {
                    //Saves the error message with the reason why the password is incorrect.
                    message = userService.messageIncorrectPassword(newUser.getUserPassword());
                    LOGGER.info(String.valueOf(message));

                    return new ResponseEntity<>(message, HttpStatus.CONFLICT);
                }
            } else {
                LOGGER.info("Self-service-stand name " + newUser.getSelfServiceStandName() + " already exists.");
                message.put("Message", "Self-service-stand name " + newUser.getSelfServiceStandName() + " already exists.");

                return new ResponseEntity<>(message, HttpStatus.CONFLICT);
            }
        } else {

            LOGGER.info("User " + newUser.getUserName() + " already exists.");
            message.put("Message", "User " + newUser.getUserName() + " already exists.");

            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }

    /**
     * A new user is created, the user is automatically added to the self-condition status that the adder has.
     * A new user can only be created if there is not yet a user with the name.
     * The password and the email address are checked for correctness.
     *
     * @param newUser private String newUserName;
     *                private String newUserPassword;
     *                private String newUserEmail;
     *                private String newUserRole;
     *                private String addingSelfServiceStandName;
     *                private String addingUser;
     * @return message, httpStatus or new user
     */
    @PostMapping("/newUser")
    public ResponseEntity<?> newUser(@RequestBody NewUserRequest newUser) {

        //It will remove excess spaces.
        newUser.setNewUserName(newUser.getNewUserName().trim());

        //create new json object
        JSONObject message = new JSONObject();

        //self-service-stand nr
        int selfServiceStandNr = selfServiceStandService.getSelfServiceStandNr(newUser.getAddingSelfServiceStandName());

        //Checks whether the user who is adding a user is an admin.
        if (selfServiceStandUserNrRepository.existsBySelfServiceStandNr_SelfServiceStandNIdAndUserNr_UserNameAndClRoleNr_RoleName(selfServiceStandNr, newUser.getAddingUser(), ADMINROLE)) {

            //It is checked whether the username has not yet been assigned.
            if (!userRepository.existsByUserName(newUser.getNewUserName())) {

                //Checks whether the password meets the requirements.
                if (userService.checkPasswordCorrectly(newUser.getNewUserPassword())) {

                    //check email correctly
                    if (userService.checkEmailCorrectly(newUser.getNewUserEmail())) {

                        //create new User
                        User user = userService.createUser(newUser.getNewUserName(), newUser.getNewUserPassword(), newUser.getNewUserEmail());

                        //Connections self-service stand
                        userService.setConnectionsSelfServiceStand(newUser.getNewUserName(), selfServiceStandNr, newUser.getNewUserRole());

                        LOGGER.info("User " + newUser.getNewUserName() + "created.");

                        return new ResponseEntity<>(user, HttpStatus.CREATED);

                    } else {
                        LOGGER.info("The email " + newUser.getNewUserEmail() + " address you entered is incorrect.");
                        message.put("Message", "The email " + newUser.getNewUserEmail() + " address you entered is incorrect.");

                        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
                    }
                } else {
                    //Saves the error message with the reason why the password is incorrect.
                    message = userService.messageIncorrectPassword(newUser.getNewUserPassword());
                    LOGGER.info(String.valueOf(message));

                    return new ResponseEntity<>(message, HttpStatus.CONFLICT);
                }
            } else {
                LOGGER.info("User " + newUser.getNewUserName() + " already exists.");
                message.put("Message", "User " + newUser.getNewUserName() + " already exists.");

                return new ResponseEntity<>(message, HttpStatus.CONFLICT);
            }
        } else {
            LOGGER.info("The user is not allowed to add any further users because he is not an admin.");
            message.put("Message", "The user is not allowed to add any further users because he is not an admin.");

            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }

    /**
     * Method changes the user data.
     * Username can only be changed if it is not used.
     *
     * @param user private String userName;
     *             private String userPassword;
     *             private String selfServiceStandName;
     *             private String email;
     *             private String changeUserName;
     * @return message and httpStatus
     */
    @PutMapping("/")
    public ResponseEntity<?> changeUser(@RequestBody UserRequest user) {

        //It will remove excess spaces.
        user.setUserName(user.getUserName().trim());
        user.setChangeUserName(user.getChangeUserName().trim());

        //create new json object
        JSONObject message = new JSONObject();

        //It is checked whether the username has not yet been assigned.
        if (user.getChangeUserName().isEmpty() || !userRepository.existsByUserName(user.getChangeUserName())) {

            //Checks whether the password is correct.
            if (user.getUserPassword().isEmpty() || userService.checkPasswordCorrectly(user.getUserPassword())) {

                //Checks whether the email is correct.
                if (user.getEmail().isEmpty() || userService.checkEmailCorrectly(user.getEmail())) {

                    //change user
                    User changeUser = userService.changeUserData(user);

                    return new ResponseEntity<>(changeUser, HttpStatus.OK);

                } else {
                    LOGGER.info("The email " + user.getEmail() + " address you entered is incorrect.");
                    message.put("Message", "The email " + user.getEmail() + " address you entered is incorrect.");

                    return new ResponseEntity<>(message, HttpStatus.CONFLICT);
                }
            } else {
                //Saves the error message with the reason why the password is incorrect.
                message = userService.messageIncorrectPassword(user.getUserPassword());
                LOGGER.info(String.valueOf(message));

                return new ResponseEntity<>(message, HttpStatus.CONFLICT);
            }
        } else {
            LOGGER.info("User " + user.getChangeUserName() + " already exists.");
            message.put("Message", "User " + user.getChangeUserName() + " already exists.");

            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }

    /**
     * This method returns a single user.
     *
     * @param username The username.
     * @return user
     */
    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {

        //Returns a single user.
        return new ResponseEntity<>(userRepository.findByUserName(username), HttpStatus.OK);
    }
}
