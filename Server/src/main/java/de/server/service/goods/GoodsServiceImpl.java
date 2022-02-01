package de.server.service.goods;

import de.server.model.goods.Goods;
import de.server.repository.goods.GoodsNameRepository;
import de.server.repository.goods.GoodsRepository;
import de.server.repository.goods.UnitRepository;
import de.server.repository.userAndSelfServiceStand.SelfServiceStandRepository;
import de.server.request.goods.GoodsRequest;
import de.server.service.userAndSelfServiceStand.SelfServiceStandService;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class contains methods that are used to manage the goods.
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    /**
     * Service to handle self-service stand information logic.
     */
    private final SelfServiceStandService selfServiceStandService;

    /**
     * GoodsNameRepository to handle goods name information.
     */
    private final GoodsNameRepository goodsNameRepository;

    /**
     * goodsRepository to handle goods information.
     */
    private final GoodsRepository goodsRepository;

    /**
     * selfServiceStandRepository to self-service-stand information.
     */
    private final SelfServiceStandRepository selfServiceStandRepository;

    /**
     * unitRepository to unit information.
     */
    private final UnitRepository unitRepository;


    /**
     * The constructor of the class GoodsServiceImpl.
     *
     * @param selfServiceStandService    The self-service-stand service.
     * @param goodsNameRepository        The goods name repository.
     * @param goodsRepository            The goods repository.
     * @param selfServiceStandRepository The self-service-stand repository.
     * @param unitRepository             The unit repository.
     */
    public GoodsServiceImpl(SelfServiceStandService selfServiceStandService, GoodsNameRepository goodsNameRepository, GoodsRepository goodsRepository, SelfServiceStandRepository selfServiceStandRepository, UnitRepository unitRepository) {
        this.selfServiceStandService = selfServiceStandService;
        this.goodsNameRepository = goodsNameRepository;
        this.goodsRepository = goodsRepository;
        this.selfServiceStandRepository = selfServiceStandRepository;
        this.unitRepository = unitRepository;
    }

    /**
     * Method stores a new goods.
     *
     * @param goodsRequest       String unitName;
     *                           int goodsPrice;
     *                           String goodsNote;
     *                           String selfServiceStandName;
     *                           String userName;
     * @param selfServiceStandNr self-Service-Stand nr
     * @return new goods
     */
    @Override
    public Goods saveGoods(GoodsRequest goodsRequest, int selfServiceStandNr) {
        Goods goods = new Goods();

        //set attributes
        goods.setGoodsNameNr(goodsNameRepository.findByGoodsName(goodsRequest.getGoodsName()));
        goods.setGoodsUnitNr(unitRepository.findByUnitName(goodsRequest.getUnitName()));
        goods.setGoodsPrice(goodsRequest.getGoodsPrice());
        goods.setGoodsNote(goodsRequest.getGoodsNote());
        goods.setSelfServiceStandNr(selfServiceStandRepository.findBySelfServiceStandNId(selfServiceStandNr));
        goods.setGoodsActive(goodsRequest.isGoodsActive());

        //save goods
        goodsRepository.save(goods);

        return goods;
    }

    /**
     * Method returns the goods number.
     *
     * @param selfServiceStandNr self-service-stand name
     * @param goodsName          goods name
     * @return goods number
     */
    @Override
    public int getGoodsNr(int selfServiceStandNr, String goodsName) {
        return goodsRepository.findByGoodsNameNr_GoodsNameAndSelfServiceStandNr_SelfServiceStandNId(goodsName, selfServiceStandNr).getGoodsId();
    }

    /**
     * This method changes the goods.
     *
     * @param changeGoodsRequest String goodsName;
     *                           String unitName;
     *                           int goodsPrice;
     *                           String goodsNote;
     *                           String selfServiceStandName;
     *                           String userName;
     *                           String changeGoodsName;
     * @return message
     */
    @Override
    public JSONObject updateGoods(GoodsRequest changeGoodsRequest) {

        //create new json object
        JSONObject message = new JSONObject();

        // goods nr
        int goodsNr = changeGoodsRequest.getGoodsId();

        //save self-service-stand nr
        int selfServiceStandNr = selfServiceStandService.getSelfServiceStandNr(changeGoodsRequest.getSelfServiceStandName());

        //Saves the previous record.
        Goods updateGoods = goodsRepository.findByGoodsId(goodsNr);


        //It is checked whether there are already no goods with this name and unit.
        if (!goodsRepository.existsByGoodsNameNr_GoodsNameAndSelfServiceStandNr_SelfServiceStandNameAndGoodsUnitNr_UnitName(changeGoodsRequest.getGoodsName(), changeGoodsRequest.getSelfServiceStandName(), changeGoodsRequest.getUnitName())) {

            //Checks whether the new product name is already in use.
            if (!goodsRepository.existsBySelfServiceStandNr_SelfServiceStandNIdAndGoodsNameNr_GoodsName(selfServiceStandNr, changeGoodsRequest.getGoodsName())) {

                updateGoods.setGoodsNameNr(goodsNameRepository.findByGoodsName(changeGoodsRequest.getGoodsName()));

                message.put("ChangeMessage", "The name of the goods has been changed.");
            } else {
                message.put("Message", "The goods " + changeGoodsRequest.getGoodsName() + " with the name already exist.");
            }
        }
        if (changeGoodsRequest.getUnitName() != null) {
            updateGoods.setGoodsUnitNr(unitRepository.findByUnitName(changeGoodsRequest.getUnitName()));
            message.put("ChangeMessage", "The unit of the goods was changed.");
        }


        if (changeGoodsRequest.getGoodsPrice() != 0) {

            //// Checks whether a valid name has been entered.
            if (changeGoodsRequest.getGoodsPrice() >= 0) {
                updateGoods.setGoodsPrice(changeGoodsRequest.getGoodsPrice());
                message.put("ChangeMessage", "The price of the goods was changed.");
            } else {
                message.put("Message", "The price quoted is invalid. This must be greater than or equal to zero.");
            }
        }
        //set aktiv
        updateGoods.setGoodsActive(changeGoodsRequest.isGoodsActive());

        //update the goods
        goodsRepository.save(updateGoods);

        return message;
    }


    /**
     * Method deletes a goods entry, but only if it has not yet been used in a table
     *
     * @param deleteGoodsRequest goods name, user name, self-Service-stand-name
     * @return goods delete, true or false
     */
    public boolean deleteGoodsEntry(GoodsRequest deleteGoodsRequest) {

        boolean goodDelete = false;
        // goods nr
        int goodsNr = deleteGoodsRequest.getGoodsId();

        Goods deleteGoods = new Goods();
        deleteGoods.setGoodsId(goodsNr);

        //Delete goods at the database
        goodsRepository.delete(deleteGoods);

        goodDelete = true;

        return goodDelete;
    }

    /**
     * A list of all goods that are assigned to the self-service stand.
     *
     * @param selfServiceStandName self-service-stand-name
     * @return list with goods
     */
    public List<Goods> getGoodsEntryList(String selfServiceStandName) {
        return goodsRepository.findBySelfServiceStandNr_SelfServiceStandNIdOrderByGoodsNameNr_GoodsNameAsc(selfServiceStandService.getSelfServiceStandNr(selfServiceStandName));
    }

    /**
     * Returns a single Goods.
     *
     * @param selfServiceStandName self-service-stand-name
     * @param goodsName            goods name
     * @return Returns a single Goods.
     */
    public Goods getOneGoodsEntry(String selfServiceStandName, String goodsName) {
        return goodsRepository.findByGoodsNameNr_GoodsNameAndSelfServiceStandNr_SelfServiceStandNId(goodsName, selfServiceStandService.getSelfServiceStandNr(selfServiceStandName));
    }

    /**
     * Return of the goods that are on sale
     *
     * @param selfServiceStandName self-service-stand-name
     * @return list with goods
     */
    public List<Goods> getGoodsEntryListActive(String selfServiceStandName) {
        return goodsRepository.findBySelfServiceStandNr_SelfServiceStandNameAndGoodsActiveOrderByGoodsNameNr_GoodsNameAsc(selfServiceStandName, true);
    }

}
