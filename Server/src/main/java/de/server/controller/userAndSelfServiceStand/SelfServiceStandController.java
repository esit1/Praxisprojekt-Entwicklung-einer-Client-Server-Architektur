package de.server.controller.userAndSelfServiceStand;

import de.server.model.userAndSelfServiceStand.User;
import de.server.repository.userAndSelfServiceStand.SelfServiceStandRepository;
import de.server.request.userAndSelfServiceStand.SelfServiceStandRequest;
import de.server.response.userAndSelfServiceStand.NameSelfServiceStandResponse;
import de.server.service.userAndSelfServiceStand.SelfServiceStandService;
import de.server.service.userAndSelfServiceStand.UserService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * With the help of this class, a new self-service stand can be created, deleted, and a user list can be output.
 */
@RestController
@RequestMapping("/selfServiceStand")
public class SelfServiceStandController {

    /**
     * Editor role
     */
    private static final String EDITORROLE = "Editor";

    /**
     * Logger of the GoodsNameController class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SelfServiceStandController.class);

    /**
     * SelfServiceStandRepository to selfServiceStand information.
     */
    private final SelfServiceStandRepository selfServiceStandRepository;
    /**
     * Service provides the self-service-stand logic.
     */
    private final SelfServiceStandService selfServiceStandService;

    /**
     * Service provides the user logic.
     */
    private final UserService userService;

    /**
     *
     * @param selfServiceStandRepository selfServiceStandRepository
     * @param selfServiceStandService selfServiceStandService
     * @param userService userService
     */
    public SelfServiceStandController(SelfServiceStandRepository selfServiceStandRepository, SelfServiceStandService selfServiceStandService, UserService userService) {
        this.selfServiceStandRepository = selfServiceStandRepository;
        this.selfServiceStandService = selfServiceStandService;
        this.userService = userService;
    }

    /**
     * This method adds an existing user to an already existing self-service stand.
     * The user who adds a user must have admin rights.
     * Every user cannot be added to self-service statuses that have the same name.
     *
     * @return message and httpStatus
     */
    @PostMapping("/addUser/")
    public ResponseEntity<?> addUser(@RequestBody SelfServiceStandRequest selfServiceStandRequest) {

        //create new json object
        JSONObject message = new JSONObject();

        //It is checked whether the user is admin.
        if (userService.checkUserAdmin(selfServiceStandRequest.getUserName(), selfServiceStandRequest.getSelfServiceStandName())) {

            //The user is added to the self-service stand.
            selfServiceStandService.createNewShortcut(selfServiceStandRequest.getAddingUserName(), selfServiceStandService.getSelfServiceStandNr(selfServiceStandRequest.getSelfServiceStandName()), EDITORROLE);

            LOGGER.info("The user " + selfServiceStandRequest.getAddingUserName() + " has been added to the self-service stand " + selfServiceStandRequest.getSelfServiceStandName() + " .");
            message.put("Message", "The user " + selfServiceStandRequest.getUserName() + " has been added to the self-service stand " + selfServiceStandRequest.getSelfServiceStandName() + " .");

            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            LOGGER.info("The user " + selfServiceStandRequest.getUserName() + " has no admin rights and is not allowed to add a user " + selfServiceStandRequest.getAddingUserName() + " .");
            message.put("Message", "The user " + selfServiceStandRequest.getUserName() + " has no admin rights and is not allowed to add a user " + selfServiceStandRequest.getAddingUserName() + " .");

            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }

    /**
     * This function removes a user from an existing self-service stand.
     * The user who removes this must have admin rights.
     *
     * @param removeUserName       The name of the user to be removed.
     * @param userName             The name of the user which one user removes.
     * @param selfServiceStandName The self-service-stand name.
     * @return message and httpStatus
     */
    @DeleteMapping("/removeUser/{removeUserName}/{userName}/{selfServiceStandName}")
    public ResponseEntity<?> removeUser(@PathVariable String removeUserName, @PathVariable String userName, @PathVariable String selfServiceStandName) {
        //create new json object
        JSONObject message = new JSONObject();

        //It is checked whether the user is admin.
        if (userService.checkUserAdmin(userName, selfServiceStandName)) {

            selfServiceStandService.removeUserEntry(removeUserName, selfServiceStandName);

            return new ResponseEntity<>(message, HttpStatus.OK);

        } else {
            LOGGER.info("The user " + userName + " has no admin rights and is not allowed to remove a user " + removeUserName + " .");
            message.put("Message", "The user " + userName + " has no admin rights and is not allowed to remove a user " + removeUserName + " .");

            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }

    /**
     * This method creates a new self-service booth and adds the user. The user receives admin rights.
     *
     * @param selfServiceStandName The self-servie-stand name.
     * @param userName             The user name.
     * @return message and httpStatus
     */
    @PostMapping("/{userName}/{selfServiceStandName}")
    public ResponseEntity<?> createSelfServiceStand(@PathVariable String selfServiceStandName, @PathVariable String userName) {
        //create new json object
        JSONObject message = new JSONObject();

        //It is checked whether the Self-service-stand name has not yet been assigned.
        if (!selfServiceStandRepository.existsBySelfServiceStandName(selfServiceStandName)) {

            selfServiceStandService.createSelfServiceStandEntry(selfServiceStandName, userName);

            LOGGER.info("The self-service stand " + selfServiceStandName + " was created.");
            message.put("Message", "The self-service stand " + selfServiceStandName + " was created.");

            return new ResponseEntity<>(message, HttpStatus.OK);

        } else {
            LOGGER.info("Self-service-stand name " + selfServiceStandName + " already exists.");
            message.put("Message", "Self-service-stand name " + selfServiceStandName + " already exists.");

            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }

    /**
     * This method provides a list of users who belong to the self-service stand.
     *
     * @param selfServiceStandName The self-service-stand name.
     * @return A list with users.
     */
    @GetMapping("/{selfServiceStandName}")
    public ResponseEntity<List<User>> getListOfUser(@PathVariable String selfServiceStandName) {

        return new ResponseEntity<>(userService.getListUser(selfServiceStandName), HttpStatus.OK);
    }

    /**
     * Return a List with self-Service-Stand
     *
     * @param userName The user name.
     * @return list
     */
    @GetMapping("list/{userName}")
    public ResponseEntity<List<NameSelfServiceStandResponse>> getListOfSelfServiceStand(@PathVariable String userName) {

        return new ResponseEntity<>(selfServiceStandService.getListOfSelfServiceStand(userName), HttpStatus.OK);
    }
}







