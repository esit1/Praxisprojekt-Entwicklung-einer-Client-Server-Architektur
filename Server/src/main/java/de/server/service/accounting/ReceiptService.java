package de.server.service.accounting;

import de.server.model.accounting.ReceiptGoods;
import de.server.request.accounting.ReceiptGoodsRequest;
import de.server.response.accounting.ReceiptResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * This interface service contains methods that serve to change, add and delete the goods.
 */
@Service
public interface ReceiptService {

    /**
     * Changes an receipt goods entry.
     *
     * @param receiptGoodsRequest receiptGoodsInput
     * @return true
     */
    boolean changeReceipt(ReceiptGoodsRequest receiptGoodsRequest);

    /**
     * Deletes an entry receipt from the table.
     *
     * @param receiptGoodsNr receipt nr
     * @return true
     */
    boolean deleteReceipt(int receiptGoodsNr);

    /**
     * Saves the new goods receipt.
     *
     * @param receiptGoodsRequest LocalDate receiptDate;
     *                            int goodsPieces;
     *                            String goodsName;
     *                            String userName;
     *                            String selfServiceStandName;
     * @return new receipt
     */
    ReceiptGoods newReceipt(ReceiptGoodsRequest receiptGoodsRequest);

    /**
     * Returns a receipt entry.
     *
     * @param receiptGoodsNr receipt Id
     * @return one receipt good
     */
    ReceiptGoods getOneReceiptEntry(int receiptGoodsNr);

    /**
     * Returns all incoming goods, a SelfServiceStand, sorted by date.
     *
     * @param selfServiceStandName selfServiceStandName
     * @return list receipt goods
     */
    List<ReceiptResponse> getAllReceiptEntry(String selfServiceStandName);

    /**
     * Returns all goods receipts for a product, a self-service stand, sorted by date.
     *
     * @param selfServiceStandName selfServiceStandName
     * @return list receipt goods
     */
    List<ReceiptGoods> getAllGoodsNameReceiptEntry(String selfServiceStandName, String goodsName);

    /**
     * Method checks whether the number of goods available is greater than the outflow.
     *
     * @param receiptDate receiptDate
     * @param goodsPieces goodsPieces
     * @param goodsID     goodsID
     * @return boolean
     */
    boolean checkSumGoodsAvailable(LocalDate receiptDate, int goodsPieces, int goodsID);
}
