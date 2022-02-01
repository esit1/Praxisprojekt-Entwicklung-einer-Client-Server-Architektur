package de.server.response.accounting;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReceiptResponse {

    int receiptGoodId;
    LocalDate dateReceipt;
    int goodsPieces;
    String goodsName;
    String goodsUnit;
    String userName;

    public ReceiptResponse(int receiptGoodId, LocalDate dateReceipt, int goodsPieces, String goodsName, String goodsUnit, String userName) {
        this.receiptGoodId = receiptGoodId;
        this.dateReceipt = dateReceipt;
        this.goodsPieces = goodsPieces;
        this.goodsName = goodsName;
        this.goodsUnit = goodsUnit;
        this.userName = userName;
    }
}
