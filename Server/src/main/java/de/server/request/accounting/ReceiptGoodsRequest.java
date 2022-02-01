package de.server.request.accounting;

import lombok.Getter;

import java.time.LocalDate;

/**
 * This class contains all goods entries for the Goods interface that are required to create, change and delete goods receipt.
 */
@Getter
public class ReceiptGoodsRequest {

    /**
     * The receipt ID.
     */
    int receiptID;

    /**
     * The receipt date.
     */
    LocalDate receiptDate;

    /**
     * Number of goods added or removed.
     */
    int goodsPieces;

    /**
     * Name of the goods.
     */
    int goodsID;

    /**
     * The name of the user.
     */
    String userName;

    /**
     * The name of the self-service stand name.
     */
    String selfServiceStandName;
}
