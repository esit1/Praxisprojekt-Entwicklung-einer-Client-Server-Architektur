package de.server.service.goods;

import de.server.model.goods.GoodsName;
import de.server.repository.goods.GoodsNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class contains methods that are used to manage the goods name.
 */
@Service
public class GoodsNameServiceImpl implements GoodsNameService {

    /**
     * GoodsNameRepository to handle goods name information.
     */
    private final GoodsNameRepository goodsNameRepository;


    public GoodsNameServiceImpl(GoodsNameRepository goodsNameRepository) {
        this.goodsNameRepository = goodsNameRepository;
    }

    /**
     * Method returns a sorted list with all goods names.
     *
     * @return json list with all goods names
     */
    public List<GoodsName> getGoodsNameList() {
        return goodsNameRepository.findByOrderByGoodsNameAsc();
    }

    /**
     * Method creates a new goods name
     *
     * @param goodsName Name of the goods name.
     * @return goods name
     */
    public GoodsName createGoodsNameEntry(String goodsName) {

        return goodsNameRepository.save(new GoodsName(goodsName));
    }

    /**
     * The method only deletes a goods name.
     *
     * @param goodsName The goods name.
     * @return delete Entry true or false.
     */
    public boolean deleteGoodsNameEntry(String goodsName) {
        boolean deleteEntry = false;

        if (goodsNameRepository.existsByGoodsNameIs(goodsName)) {
            GoodsName deleteGoodsName = new GoodsName();
            deleteGoodsName.setGoodsNameId(goodsNameRepository.findByGoodsName(goodsName).getGoodsNameId());

            //Delete goods name at the database
            goodsNameRepository.delete(deleteGoodsName);

            deleteEntry = true;
        }
        return deleteEntry;
    }

    /**
     * The method only changes the name of a goods name.
     *
     * @param oldGoodsName The old goods name.
     * @param newGoodsName The new goods name.
     * @return The new goods name.
     */
    public GoodsName updateGoodsNameEntry(String oldGoodsName, String newGoodsName) {

        GoodsName changeGoodsName = new GoodsName();
        changeGoodsName.setGoodsNameId(goodsNameRepository.findByGoodsName(oldGoodsName).getGoodsNameId());

        changeGoodsName.setGoodsName(newGoodsName);

        //change goods name at the database
        goodsNameRepository.save(changeGoodsName);

        return changeGoodsName;
    }
}
