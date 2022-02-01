package de.server.request.goods;

import lombok.Getter;
import lombok.Setter;

/**
 * Input from the "Goods" interface.
 */
@Getter
@Setter
public class GoodsRequest {

    /**
     * The name of the goods.
     */
    String goodsName;

    /**
     * The name of the unit of goods.
     */
    String unitName;

    /**
     * The price of the goods.
     */
    double goodsPrice;

    /**
     * The goods note.
     */
    String goodsNote;

    /**
     * The name of the self-service stand.
     */
    String selfServiceStandName;

    /**
     * The name of the user.
     */
    String userName;

    /**
     * The goods id.
     */
    int goodsId;

    /**
     * Goods on sale
     */
    boolean goodsActive;
}
