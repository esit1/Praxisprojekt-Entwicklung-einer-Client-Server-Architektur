package de.server.controller.goods;

import de.server.model.goods.GoodsName;
import de.server.repository.goods.GoodsNameRepository;
import de.server.repository.goods.GoodsRepository;
import de.server.service.goods.GoodsNameService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The role Controller class outputs a list of all existing goods name.
 * It is possible to create new goods name.
 * Goods name can also be changed and deleted, but only if they are not yet used in another table.
 *
 */
@RestController
@RequestMapping("/goodsName")
public class GoodsNameController {

    /**
     * Logger of the GoodsNameController class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsNameController.class);
    /**
     * Service to handle goods name service .
     */
    private final GoodsNameService goodsNameService;

    /**
     * GoodsNameRepository to handle goods name information.
     */
    private final GoodsNameRepository goodsNameRepository;

    /**
     * goodsRepository to handle goods information.
     */
    private final GoodsRepository goodsRepository;

    public GoodsNameController(GoodsNameService goodsNameService, GoodsNameRepository goodsNameRepository, GoodsRepository goodsRepository) {
        this.goodsNameService = goodsNameService;
        this.goodsNameRepository = goodsNameRepository;
        this.goodsRepository = goodsRepository;
    }

    /**
     * Method returns a sorted list with all goods names.
     *
     * @return json list with all goods names
     */
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Object> getGoodsNames() {

        //Query sorted list, sorted by goods names
        return new ResponseEntity<>(goodsNameService.getGoodsNameList(), HttpStatus.OK);
    }

    /**
     * Method creates a new goods name, but only if it does not yet exist.
     *
     * @param goodsName Name of the goods name
     * @return Http Status code: Create if a new goods name has been created, otherwise no content
     * and created goods name.
     */
    @PostMapping(path = "/{goodsName}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<GoodsName> createGoodsName(@PathVariable String goodsName) {

        //excess whitespace is removed
        goodsName = goodsName.trim();

        GoodsName newGoodsName = null;

        //It is checked whether the goods name already exists.
        if (!goodsNameRepository.existsByGoodsNameIs(goodsName)) {

            //save the new goods name
            newGoodsName = goodsNameService.createGoodsNameEntry(goodsName);

            LOGGER.info("The goods name " + goodsName + " has been created.");
        } else {
            LOGGER.info("The goods name " + goodsName + " is already there.");
        }

        // Set the http status by goods name; CREATED if it is null, NO_CONTENT otherwise
        final HttpStatus httpStatus = null != newGoodsName
                ? HttpStatus.CREATED
                : HttpStatus.NO_CONTENT;

        return new ResponseEntity<>(newGoodsName, httpStatus);
    }

    /**
     * The method only deletes a goods name from the list if it has not yet been used.
     *
     * @param goodsName goods name
     * @return message in Json format and Http status code
     */
    @DeleteMapping(path = "/{goodsName}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<JSONObject> deleteGoodsName(@PathVariable String goodsName) {

        //create new json object
        JSONObject message = new JSONObject();

        //excess whitespace is removed
        goodsName = goodsName.trim();

        //It is checked whether the goods name is not used in the "goods" table.
        if (!goodsRepository.existsByGoodsNameNr_GoodsName(goodsName)) {

            goodsNameService.deleteGoodsNameEntry(goodsName);

            LOGGER.info("The goods name " + goodsName + " has been deleted.");
            message.put("Message", "The goods name " + goodsName + " has been deleted.");
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            LOGGER.info("The goods name " + goodsName + " is already in use. Deletion is no longer possible.");
            message.put("Message", "The goods name " + goodsName + " is already in use. Deletion is no longer possible.");
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }

    /**
     * The method only changes the name of a goods name if it has not yet been used in the Goods table.
     *
     * @param oldGoodsName old goods name
     * @param newGoodsName new goods name
     * @return message in Json format and Http status code
     */
    @PutMapping(path = "/{oldGoodsName}/{newGoodsName}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<JSONObject> updateGoodsName(@PathVariable String oldGoodsName, @PathVariable String newGoodsName) {
        //create new json object
        JSONObject message = new JSONObject();

        //excess whitespace is removed
        oldGoodsName = oldGoodsName.trim();
        newGoodsName = newGoodsName.trim();

        //It is checked whether the goods name is not used in the "goods" table.
        if (!goodsRepository.existsByGoodsNameNr_GoodsName(oldGoodsName)) {

            goodsNameService.updateGoodsNameEntry(oldGoodsName, newGoodsName);

            LOGGER.info("The name of the goods name has been changed from " + oldGoodsName + " to " + newGoodsName + ".");
            message.put("Message", "The name of the goods name has been changed from " + oldGoodsName + " to " + newGoodsName + ".");
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            LOGGER.info("The goods name " + oldGoodsName + " is already in use. Change is no longer possible.");
            message.put("Message", "The goods name " + oldGoodsName + " is already in use. Change is no longer possible.");
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }
}
