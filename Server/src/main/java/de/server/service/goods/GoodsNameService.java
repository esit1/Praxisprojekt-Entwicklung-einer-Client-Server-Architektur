package de.server.service.goods;

import de.server.model.goods.GoodsName;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This interface service contains methods that serve to change, add and delete the goods name.
 */
@Service
public interface GoodsNameService {

    /**
     * The method only changes the name of a goods name.
     *
     * @param oldGoodsName The old goods name.
     * @param newGoodsName The new goods name.
     * @return The new goods name.
     */
    GoodsName updateGoodsNameEntry(String oldGoodsName, String newGoodsName);

    /**
     * The method only deletes a goods name.
     *
     * @param goodsName The goods name.
     * @return delete Entry true or false.
     */
    boolean deleteGoodsNameEntry(String goodsName);

    /**
     * Method creates a new goods name
     *
     * @param goodsName Name of the goods name.
     * @return goods name
     */
    GoodsName createGoodsNameEntry(String goodsName);

    /**
     * Method returns a sorted list with all goods names.
     *
     * @return json list with all goods names
     */
    List<GoodsName> getGoodsNameList();
}
