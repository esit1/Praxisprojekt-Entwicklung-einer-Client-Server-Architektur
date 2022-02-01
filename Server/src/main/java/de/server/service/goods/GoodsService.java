package de.server.service.goods;

import de.server.model.goods.Goods;
import de.server.request.goods.GoodsRequest;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This interface service contains methods that serve to change, add and delete the goods.
 */
@Service
public interface GoodsService {

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
    Goods saveGoods(GoodsRequest goodsRequest, int selfServiceStandNr);

    /**
     * Method returns the goods number.
     *
     * @param selfServiceStandNr self-service-stand name
     * @param goodsName          goods name
     * @return goods number
     */
    int getGoodsNr(int selfServiceStandNr, String goodsName);

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
    JSONObject updateGoods(GoodsRequest changeGoodsRequest);

    /**
     * Returns a single Goods.
     *
     * @param selfServiceStandName self-service-stand-name
     * @param goodsName            goods name
     * @return Returns a single Goods.
     */
    Goods getOneGoodsEntry(String selfServiceStandName, String goodsName);

    /**
     * A list of all goods that are assigned to the self-service stand.
     *
     * @param selfServiceStandName self-service-stand-name
     * @return list with goods
     */
    List<Goods> getGoodsEntryList(String selfServiceStandName);

    /**
     * Method deletes a goods entry, but only if it has not yet been used in a table
     *
     * @param deleteGoodsRequest goods name, user name, self-Service-stand-name
     * @return goods delete, true or false
     */
    boolean deleteGoodsEntry(GoodsRequest deleteGoodsRequest);

    /**
     * Return of the goods that are on sale
     *
     * @param selfServiceStandName self-service-stand-name
     * @return list with goods
     */
    List<Goods> getGoodsEntryListActive(String selfServiceStandName);
}
