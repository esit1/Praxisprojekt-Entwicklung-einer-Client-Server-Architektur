package de.server.model.goods;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Class represents the table "tb_goods_name".
 */
@Setter
@Getter
@Table(name = "tb_goods_name")
@Entity
public class GoodsName {

    /**
     * The goods name ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cl_goods_name_nr", nullable = false)
    private Integer goodsNameId;


    /**
     * Name of the goods.
     */
    @Lob
    @Column(name = "cl_goods_name", nullable = false)
    private String goodsName;

    /**
     * Constructor sets the goods name.
     *
     * @param goodsName Name of the goods.
     */
    public GoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * An empty constructor.
     */
    public GoodsName() {
    }
}