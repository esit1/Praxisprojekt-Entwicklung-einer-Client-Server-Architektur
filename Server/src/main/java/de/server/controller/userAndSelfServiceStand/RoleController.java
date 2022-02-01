package de.server.controller.userAndSelfServiceStand;

import de.server.repository.userAndSelfServiceStand.RoleRepository;
import de.server.request.userAndSelfServiceStand.RoleRequest;
import de.server.service.userAndSelfServiceStand.UserService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * With the help of this class, a list of roles can be output, this role can be changed.
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    /**
     * Logger of the GoodsNameController class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
    /**
     * tbRoleRepository to handle user role information.
     */
    private final RoleRepository roleRepository;

    /**
     * Service provides the user logic.
     */
    private final UserService userService;

    /**
     *
     * @param roleRepository roleRepository
     * @param userService userService
     */
    public RoleController(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    /**
     * The getRole method outputs a Json list with all existing roles.
     *
     * @return json list with user roles
     */
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Object> getRoles() {

        //Query sorted list, sorted by role name
        return new ResponseEntity<>(roleRepository.getByOrderByRoleNameAsc(), HttpStatus.OK);
    }

    /**
     * This function changes the user role of a user.
     * The user who wants to change this must have admin rights.
     *
     * @param roleRequest String changeUserName;
     *                    String userName;
     *                    String selfServiceStandName;
     *                    String newRole;
     * @return message and httpStatus.
     */
    @PutMapping("/userRole/")
    public ResponseEntity<?> changeRole(@RequestBody RoleRequest roleRequest) {

        //create new json object
        JSONObject message = new JSONObject();

        //It is checked whether the user is admin.
        if (userService.checkUserAdmin(roleRequest.getUserName(), roleRequest.getSelfServiceStandName())) {

            //change the role
            userService.changeRole(roleRequest.getSelfServiceStandName(), roleRequest.getNewRole(), roleRequest.getChangeUserName());

            LOGGER.info("The user role of the user " + roleRequest.getChangeUserName() + " has been changed.");
            message.put("Message", "The user role of the user " + roleRequest.getChangeUserName() + " has been changed.");

            return new ResponseEntity<>(message, HttpStatus.OK);

        } else {
            LOGGER.info("The user " + roleRequest.getChangeUserName() + " has no admin rights and is not allowed to change a role.");
            message.put("Message", "The user " + roleRequest.getChangeUserName() + " has no admin rights and is not allowed to change a role.");

            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }
}
