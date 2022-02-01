package de.server.controller.goods;

import de.server.model.goods.Goods;
import de.server.repository.goods.GoodsRepository;
import de.server.request.goods.GoodsRequest;
import de.server.service.goods.GoodsService;
import de.server.service.userAndSelfServiceStand.SelfServiceStandService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The class manages the goods, goods can be created, deleted, changed, and different lists can be issued.
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    /**
     * Logger of the GoodsNameController class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsController.class);

    /**
     * Service provides the goods logic.
     */
    private final GoodsService goodsService;

    /**
     * goodsRepository to handle goods information.
     */
    private final GoodsRepository goodsRepository;

    /**
     * Service provides the self-service-stand logic.
     */
    private final SelfServiceStandService selfServiceStandService;

    /**
     *
     * @param goodsService goodsService
     * @param goodsRepository goodsRepository
     * @param selfServiceStandService selfServiceStandService
     */
    public GoodsController(GoodsService goodsService, GoodsRepository goodsRepository, SelfServiceStandService selfServiceStandService) {
        this.goodsService = goodsService;
        this.goodsRepository = goodsRepository;
        this.selfServiceStandService = selfServiceStandService;
    }

    /**
     * Method adds new goods. If a goods with the same name is already in the table, this is not possible.
     *
     * @param newGoodsRequest String goodsName;
     *                        String unitName;
     *                        int goodsPrice;
     *                        String goodsNote;
     *                        String selfServiceStandName;
     *                        String userName;
     * @return message and httpStatus
     */
    @PostMapping("/")
    private ResponseEntity<Goods> addGoods(@RequestBody GoodsRequest newGoodsRequest) {

        Goods newGoods = null;

        //save self-service-stand nr
        int selfServiceStandNr = selfServiceStandService.getSelfServiceStandNr(newGoodsRequest.getSelfServiceStandName());

        //It is checked whether there are already no goods with this name and unit.
        if (!goodsRepository.existsByGoodsNameNr_GoodsNameAndSelfServiceStandNr_SelfServiceStandNameAndGoodsUnitNr_UnitName(newGoodsRequest.getGoodsName(), newGoodsRequest.getSelfServiceStandName(), newGoodsRequest.getUnitName())) {

            newGoods = goodsService.saveGoods(newGoodsRequest, selfServiceStandNr);

            LOGGER.info("The goods " + newGoodsRequest.getGoodsName() + " was created.");

        } else {
            LOGGER.info("A product " + newGoodsRequest.getGoodsName() + " with the name and Unit already exists.");
        }
        // Set the http status by goods name; CREATED if it is null, NO_CONTENT otherwise
        final HttpStatus httpStatus = null != newGoods
                ? HttpStatus.CREATED
                : HttpStatus.NO_CONTENT;

        return new ResponseEntity<>(newGoods, httpStatus);
    }

    /**
     * This method deletes a product, but it can only be deleted if it is not used.
     *
     * @param deleteGoodsRequest goods name, user name, self-Service-stand-name
     * @return message and httpStatus
     */
    @PostMapping("/delete/")
    private ResponseEntity<JSONObject> deleteGoods(@RequestBody GoodsRequest deleteGoodsRequest) {

        //create new json object
        JSONObject message = new JSONObject();

        //Entry is deleted, but only if it is not yet used.
        if (goodsService.deleteGoodsEntry(deleteGoodsRequest)) {

            LOGGER.info("The goods " + deleteGoodsRequest.getGoodsName() + " have been deleted.");
            message.put("Message", "The goods " + deleteGoodsRequest.getGoodsName() + " have been deleted.");
            return new ResponseEntity<>(message, HttpStatus.OK);

        } else {
            LOGGER.info("The goods " + deleteGoodsRequest.getGoodsName() + " can no longer be deleted because they are already in use.");
            message.put("Message", "The goods " + deleteGoodsRequest.getGoodsName() + " can no longer be deleted because they are already in use.");
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }

    /**
     * Change the goods, the name and the unit can only be changed if it is not used.
     *
     * @param changeGoodsRequest String goodsName;
     *                           String unitName;
     *                           int goodsPrice;
     *                           String goodsNote;
     *                           String selfServiceStandName;
     *                           String userName;
     *                           String changeGoodsName;
     * @return message and httpStatus
     */
    @PostMapping("/update/")
    private ResponseEntity<JSONObject> changeGoods(@RequestBody GoodsRequest changeGoodsRequest) {

        //update goods
        JSONObject message = goodsService.updateGoods(changeGoodsRequest);


        if (message.containsKey("ChangeMessage")) {
            LOGGER.info("The goods " + changeGoodsRequest.getGoodsName() + " have been changed.");
            message.put("Message", "The goods " + changeGoodsRequest.getGoodsName() + " have been changed.");
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            LOGGER.info("The goods " + changeGoodsRequest.getGoodsName() + " were not changed. ");
            message.put("Message", "The goods " + changeGoodsRequest.getGoodsName() + " were not changed.");
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }

    /**
     * A list of all goods that are assigned to the self-service stand.
     *
     * @param selfServiceStandName self-service-stand-name
     * @return list with goods
     */
    @GetMapping("/{selfServiceStandName}")
    private ResponseEntity<Object> getGoods(@PathVariable String selfServiceStandName) {

        //Query sorted list, sorted by goods name
        return new ResponseEntity<>(goodsService.getGoodsEntryList(selfServiceStandName), HttpStatus.OK);
    }

  /**
     * Returns a single Goods.
     *
     * @param selfServiceStandName self-service-stand-name
     * @param goodsName            goods name
     * @return Returns a single Goods.
     */
    @GetMapping("/{selfServiceStandName}/{goodsName}")
    private ResponseEntity<Goods> getOneGoods(@PathVariable String selfServiceStandName, @PathVariable String goodsName) {

        //Returns a single Goods.
        return new ResponseEntity<>(goodsService.getOneGoodsEntry(selfServiceStandName, goodsName), HttpStatus.OK);
    }

    /**
     * Goods on sale return list
     *
     * @param selfServiceStandName selfServiceStandName
     * @return liste with aktive goods
     */
    @GetMapping("/active/{selfServiceStandName}")
    private ResponseEntity<?> activeGoods(@PathVariable String selfServiceStandName) {

        return new ResponseEntity<>(goodsService.getGoodsEntryListActive(selfServiceStandName), HttpStatus.OK);
    }
}
