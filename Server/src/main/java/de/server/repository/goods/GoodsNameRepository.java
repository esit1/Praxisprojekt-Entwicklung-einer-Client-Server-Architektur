package de.server.repository.goods;

import de.server.model.goods.GoodsName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class contains the database queries of the table tb_goods.
 */
@Repository
public interface GoodsNameRepository extends JpaRepository<GoodsName, Integer> {

    //Query list of all goods names, sorted by unit name.
    List<GoodsName> findByOrderByGoodsNameAsc();

    //Query, goods name with the name exists.
    boolean existsByGoodsNameIs(String goodsName);

    //Query finds a goods name with the name.
    GoodsName findByGoodsName(String goodsName);

}