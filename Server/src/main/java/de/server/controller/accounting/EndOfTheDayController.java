package de.server.controller.accounting;

import de.server.controller.userAndSelfServiceStand.UserController;
import de.server.exception.InputException;
import de.server.model.accounting.EndOfTheDay;
import de.server.repository.userAndSelfServiceStand.SelfServiceStandUserNrRepository;
import de.server.request.accounting.EndOfTheDayRequest;
import de.server.response.accounting.EndOfTheDayResponse;
import de.server.service.accounting.EndOfTheDayService;
import de.server.service.userAndSelfServiceStand.SelfServiceStandService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Class creates a daily statement, this can be changed, deleted and output
 */
@RestController
@RequestMapping("/endOfDay")
public class EndOfTheDayController {

    /**
     * Administrator role number
     */
    private static final String ADMINROLE = "Administrator";

    /**
     * Logger of the GoodsNameController class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * Service provides the end of the day logic.
     */
    private final EndOfTheDayService endOfTheDayService;

    /**
     * Service provides the self-service-stand logic.
     */
    private final SelfServiceStandService selfServiceStandService;

    /**
     * SelfServiceStandUserNrRepository to self-service-stand information.
     */
    private final SelfServiceStandUserNrRepository selfServiceStandUserNrRepository;

    /**
     *
     * @param endOfTheDayService endOfTheDayService
     * @param selfServiceStandService selfServiceStandService
     * @param selfServiceStandUserNrRepository selfServiceStandUserNrRepository
     */
    public EndOfTheDayController(EndOfTheDayService endOfTheDayService, SelfServiceStandService selfServiceStandService, SelfServiceStandUserNrRepository selfServiceStandUserNrRepository) {
        this.endOfTheDayService = endOfTheDayService;
        this.selfServiceStandService = selfServiceStandService;
        this.selfServiceStandUserNrRepository = selfServiceStandUserNrRepository;
    }

    /**
     * This method helps to create the daily closing. Only an administrator can do this.
     *
     * @param endOfTheDayRequest LocalDate graduationDate; (Date of the end of the day.)
     *                           String codesName; (The name of the goods.)
     *                           String userName; (The name of the user.)
     *                           String selfServiceStandName; (The self-service-stan name.)
     *                           int graduationCount; (The counted number of goods still available.)
     * @return message and httpStatus
     */
    @PostMapping("/")
    private ResponseEntity<?> endOfDay(@RequestBody EndOfTheDayRequest endOfTheDayRequest) throws InputException {

        //Is triggered as soon as a required entry is missing.
        if (endOfTheDayRequest.getUserName().isEmpty()) throw new InputException(endOfTheDayRequest.getUserName());
        if (endOfTheDayRequest.getSelfServiceStandName().isEmpty())
            throw new InputException(endOfTheDayRequest.getSelfServiceStandName());

        //create new json object
        JSONObject message = new JSONObject();

        //self-service-stand nr
        int selfServiceStandNr = selfServiceStandService.getSelfServiceStandNr(endOfTheDayRequest.getSelfServiceStandName());

        //Checks whether the user who is adding a user is an admin.
        if (selfServiceStandUserNrRepository.existsBySelfServiceStandNr_SelfServiceStandNIdAndUserNr_UserNameAndClRoleNr_RoleName(selfServiceStandNr, endOfTheDayRequest.getUserName(), ADMINROLE)) {

            //create new entry "end of the day"
            EndOfTheDay entryEndOFDay = endOfTheDayService.carryOutCompletion(endOfTheDayRequest, selfServiceStandNr);

            LOGGER.info(String.valueOf(entryEndOFDay));

            return new ResponseEntity<>(entryEndOFDay, HttpStatus.OK);

        } else {

            LOGGER.info("The user is not allowed to carry out a daily closing because he does not have admin rights.");
            message.put("Message", "The user is not allowed to carry out a daily closing because he does not have admin rights.");

            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }

    /**
     * This method changes an entry "end of the day" can only be carried out with admin rights
     *
     * @param endOfTheDayRequest LocalDate graduationDate; (Date of the end of the day.)
     *                           String codesName; (The name of the goods.)
     *                           String userName; (The name of the user.)
     *                           String selfServiceStandName; (The self-service-stan name.)
     *                           int graduationCount; (The counted number of goods still available.)
     * @param oldDate            The old date.
     * @param oldGoodsName       The old goods name.
     * @return message and httpStatus
     */
    @PutMapping("/")
    private ResponseEntity<?> changeEndOfDay(@RequestBody EndOfTheDayRequest endOfTheDayRequest, LocalDate oldDate, String oldGoodsName) throws InputException {

        //Is triggered as soon as a required entry is missing.
        if (endOfTheDayRequest.getGraduationDate().toString().isEmpty())
            throw new InputException(endOfTheDayRequest.getGraduationDate().toString());
        if (endOfTheDayRequest.getUserName().isEmpty()) throw new InputException(endOfTheDayRequest.getUserName());
        if (endOfTheDayRequest.getSelfServiceStandName().isEmpty())
            throw new InputException(endOfTheDayRequest.getSelfServiceStandName());

        //create new json object
        JSONObject message = new JSONObject();

        //self-service-stand nr
        int selfServiceStandNr = selfServiceStandService.getSelfServiceStandNr(endOfTheDayRequest.getSelfServiceStandName());

        //Checks whether the user who is adding a user is an admin.
        if (selfServiceStandUserNrRepository.existsBySelfServiceStandNr_SelfServiceStandNIdAndUserNr_UserNameAndClRoleNr_RoleName(selfServiceStandNr, endOfTheDayRequest.getUserName(), ADMINROLE)) {

            //Changes an entry
            EndOfTheDay entry = endOfTheDayService.changeEntryEndOfDay(endOfTheDayRequest, selfServiceStandNr, oldDate, oldGoodsName);

            LOGGER.info(String.valueOf(entry));

            return new ResponseEntity<>(entry, HttpStatus.OK);

        } else {
            LOGGER.info("The user is not allowed to carry out a daily closing because he does not have admin rights.");
            message.put("Message", "The user is not allowed to carry out a daily closing because he does not have admin rights.");

            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }

    /**
     * return al list with all day of the end entries
     *
     * @param selfServiceStandName THe ent of the day request contains selfServiceStandName
     * @return list with all enties
     * @throws InputException InputException
     */
    @GetMapping("/{selfServiceStandName}")
    private ResponseEntity<List<EndOfTheDayResponse>> endOfDayList(@PathVariable String selfServiceStandName) throws InputException {

        //Is triggered as soon as a required entry is missing.
        if (selfServiceStandName.isEmpty()) throw new InputException(selfServiceStandName);

        // List<EndOfTheDayResponse> entry = endOfTheDayService.listWithAllEntry(endOfTheDayRequest.getSelfServiceStandName());
        return new ResponseEntity<>(endOfTheDayService.listWithAllEntry(selfServiceStandName), HttpStatus.OK);
    }
}
