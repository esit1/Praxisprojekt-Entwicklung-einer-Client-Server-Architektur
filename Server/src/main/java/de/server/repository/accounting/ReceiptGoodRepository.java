package de.server.repository.accounting;

import de.server.model.accounting.ReceiptGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * This class contains the database queries of the table tb_receipt_goods.
 */
@Repository
public interface ReceiptGoodRepository extends JpaRepository<ReceiptGoods, Integer> {

    /**
     * Query, finds a record with the incoming ID.
     *
     * @param receiptGoodId The receipt goods Id.
     * @return record receiptGoods
     */
    ReceiptGoods findByReceiptGoodId(Integer receiptGoodId);

    /**
     * Query, provides a list of all self-service booths that have the ID entered.
     *
     * @param selfServiceStandNId The self-service-stand Id.
     * @return list receiptGoods
     */
    List<ReceiptGoods> findBySelfServiceStandNr_SelfServiceStandNIdOrderByDateReceiptAsc(Integer selfServiceStandNId);

    /**
     * Query, provides a list of all self-service booths that have the ID and product name entered.
     *
     * @param goodsName           The goods name.
     * @param selfServiceStandNId The self-service-stand Id.
     * @return list receiptGoods
     */
    List<ReceiptGoods> findByGoodsNr_GoodsNameNr_GoodsNameAndSelfServiceStandNr_SelfServiceStandNId(String goodsName, Integer selfServiceStandNId);

    /**
     * Query, finds a list of all dates and goods In that match.
     *
     * @param dateReceipt The receipt date.
     * @param goodsId     The goods ID.
     * @return list receiptGoods
     */
    List<ReceiptGoods> findByDateReceiptAndGoodsNr_GoodsId(LocalDate dateReceipt, Integer goodsId);
}