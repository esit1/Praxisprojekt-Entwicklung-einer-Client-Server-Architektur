package de.server.model.goods;

import de.server.model.userAndSelfServiceStand.SelfServiceStand;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Class represents the table "tb_goods".
 */
@Table(name = "tb_goods", indexes = {
        @Index(name = "cl_goods_name_nr", columnList = "cl_goods_name_nr"),
        @Index(name = "cl_goods_unit_nr", columnList = "cl_goods_unit_nr"),
        @Index(name = "cl_self_service_stand_nr", columnList = "cl_self_service_stand_nr")
})

@Setter
@Getter
@Entity
public class Goods {

    /**
     * The Goods ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cl_goods_nr", nullable = false)
    private Integer goodsId;

    /**
     * The ID of a goods name.
     */
    @ManyToOne
    @JoinColumn(name = "cl_goods_name_nr")
    private GoodsName goodsNameNr;

    /**
     * The ID of a unit.
     */
    @ManyToOne
    @JoinColumn(name = "cl_goods_unit_nr")
    private Unit goodsUnitNr;

    /**
     * Price of the goods.
     */
    @Column(name = "cl_goods_price")
    private Double goodsPrice;

    /**
     * Note of the goods.
     */
    @Column(name = "cl_goods_note", nullable = false, length = 200)
    private String goodsNote;

    /**
     * The ID of a self-service stand.
     */
    @ManyToOne
    @JoinColumn(name = "cl_self_service_stand_nr")
    private SelfServiceStand selfServiceStandNr;

    /**
     * Indicator whether the goods are currently on sale.
     */
    @Column(name = "cl_goods_active", nullable = false, length = 6)
    private boolean goodsActive;
}