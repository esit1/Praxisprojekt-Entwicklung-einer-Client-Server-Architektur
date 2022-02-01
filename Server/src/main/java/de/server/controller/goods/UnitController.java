package de.server.controller.goods;

import de.server.model.goods.Unit;
import de.server.repository.goods.GoodsRepository;
import de.server.repository.goods.UnitRepository;
import de.server.service.goods.UnitService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The role Controller class outputs a list of all existing units.
 * It is possible to create new units.
 * Units can also be changed and deleted, but only if they are not yet used in another table.
 *
 */
@RestController
@RequestMapping("/unit")
public class UnitController {

    /**
     * Logger of the unitController class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UnitController.class);

    /**
     * unitRepository to handle unit information.
     */
    private final UnitRepository unitRepository;

    /**
     * goodsRepository to handle goods information.
     */
    private final GoodsRepository goodsRepository;

    /**
     * Service to handle self-service stand information logic.
     */
    private final UnitService unitService;

    public UnitController(UnitRepository unitRepository, GoodsRepository goodsRepository, UnitService unitService) {
        this.unitRepository = unitRepository;
        this.goodsRepository = goodsRepository;
        this.unitService = unitService;
    }

    /**
     * Method returns a sorted list with all units.
     *
     * @return json list with all units
     */
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Unit>> getUnits() {

        //Return sorted list, sorted by unit name.
        return new ResponseEntity<>(unitService.getUnitsList(), HttpStatus.OK);
    }

    /**
     * Method creates a new unit, but only if it does not yet exist.
     *
     * @param unitName Name of the unit
     * @return Http Status code: Create if a new unit has been created, otherwise no content
     * and created unit.
     */
    @PostMapping(path = "/{unitName}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Unit> createUnit(@PathVariable String unitName) {

        //excess whitespace is removed
        unitName = unitName.trim();

        Unit newUnit = null;

        //It is checked whether the unit already exists.
        if (!unitRepository.existsByUnitNameIs(unitName)) {

            //save and create the new unit
            newUnit = unitService.createUnitEntry(unitName);

            LOGGER.info("The unit " + unitName + " has been created.");
        } else {
            LOGGER.info("The unit " + unitName + " is already there.");
        }

        // Set the http status by unit; CREATED if it is null, NO_CONTENT otherwise
        final HttpStatus httpStatus = null != newUnit
                ? HttpStatus.CREATED
                : HttpStatus.NO_CONTENT;

        return new ResponseEntity<>(newUnit, httpStatus);
    }

    /**
     * The method only deletes a unit from the list if it has not yet been used.
     *
     * @param unitName unit name
     * @return message in Json format and Http status code
     */
    @DeleteMapping(path = "/{unitName}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<JSONObject> deleteUnit(@PathVariable String unitName) {

        //create new json object
        JSONObject message = new JSONObject();

        //excess whitespace is removed
        unitName = unitName.trim();

        //It is checked whether the unit is not used in the "goods" table.
        if (!goodsRepository.existsByGoodsUnitNr_UnitName(unitName)) {

            Unit deleteUnit = new Unit();
            deleteUnit.setUnitId(unitRepository.findByUnitName(unitName).getUnitId());

            //Delete Unit at the database
            unitService.deleteUnitEntry(unitName);

            LOGGER.info("The unit " + unitName + " has been deleted.");
            message.put("Message", "The unit " + unitName + " has been deleted.");
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            LOGGER.info("The unit " + unitName + " is already in use. Deletion is no longer possible.");
            message.put("Message", "The unit " + unitName + " is already in use. Deletion is no longer possible.");
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }

    /**
     * The method only changes the name of a unit if it has not yet been used in the Goods table.
     *
     * @param oldUnitName old unit name
     * @param newUnitName new unit name
     * @return message in Json format and Http status code
     */
    @PutMapping(path = "/{oldUnitName}/{newUnitName}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<JSONObject> updateUnit(@PathVariable String oldUnitName, @PathVariable String newUnitName) {

        //create new json object
        JSONObject message = new JSONObject();

        //excess whitespace is removed
        oldUnitName = oldUnitName.trim();
        newUnitName = newUnitName.trim();

        //It is checked whether the unit is not used in the "goods" table.
        if (!goodsRepository.existsByGoodsUnitNr_UnitName(oldUnitName)) {

            //change unit at the database
            unitService.updateUnitEntry(oldUnitName, newUnitName);

            LOGGER.info("The name of the unit has been changed from " + oldUnitName + " to " + newUnitName + ".");
            message.put("Message", "The name of the unit has been changed from " + oldUnitName + " to " + newUnitName + ".");
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            LOGGER.info("The unit " + oldUnitName + " is already in use. Change is no longer possible.");
            message.put("Message", "The unit " + oldUnitName + " is already in use. Change is no longer possible.");
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }
}
