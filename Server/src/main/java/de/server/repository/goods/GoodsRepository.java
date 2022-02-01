package de.server.repository.goods;

import de.server.model.goods.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Integer> {

    //Query whether the unit already exists.
    boolean existsByGoodsUnitNr_UnitName(String unitName);

    //Query whether the goods name already exists.
    boolean existsByGoodsNameNr_GoodsName(String goodsName);

    //Query whether the self-service-status-id and goods name exist.
    boolean existsBySelfServiceStandNr_SelfServiceStandNIdAndGoodsNameNr_GoodsName(Integer selfServiceStandNId, String goodsName);

    //Query finds a request whose self-service-status-id and goods name match.
    Goods findByGoodsNameNr_GoodsNameAndSelfServiceStandNr_SelfServiceStandNId(String goodsName, Integer selfServiceStandNId);

    //Query, sort list with all goods for which the self-service-status-id matches.
    List<Goods> findBySelfServiceStandNr_SelfServiceStandNIdOrderByGoodsNameNr_GoodsNameAsc(Integer selfServiceStandNId);

    Goods findByGoodsId(Integer goodsId);

    //It is checked whether there are already no goods with this name and unit.
    boolean existsByGoodsNameNr_GoodsNameAndSelfServiceStandNr_SelfServiceStandNameAndGoodsUnitNr_UnitName(String goodsName, String selfServiceStandName, String unitName);

    //Return of the goods that are on sale
    List<Goods> findBySelfServiceStandNr_SelfServiceStandNameAndGoodsActiveOrderByGoodsNameNr_GoodsNameAsc(String selfServiceStandName, boolean goodsActive);


}