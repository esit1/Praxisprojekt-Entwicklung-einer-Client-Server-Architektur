package de.server.model.accounting;

import de.server.model.goods.Goods;
import de.server.model.userAndSelfServiceStand.SelfServiceStand;
import de.server.model.userAndSelfServiceStand.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Table(name = "tb_receipt_goods", indexes = {
        @Index(name = "cl_user_nr", columnList = "cl_user_nr"),
        @Index(name = "cl_goods_nr", columnList = "cl_goods_nr"),
        @Index(name = "cl_self_service_stand_nr", columnList = "cl_self_service_stand_nr")
})

/**
 * Class represents the table "tb_receipt_goods".
 */
@Setter
@Getter
@Entity
public class ReceiptGoods {
    /**
     * the goods receipt id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cl_goods_receipt_nr", nullable = false)
    private Integer receiptGoodId;

    /**
     * the receipt date
     */
    @Column(name = "cl_date_receipt")
    private LocalDate dateReceipt;

    /**
     * the goods pieces
     */
    @Column(name = "cl_goods_pieces")
    private Integer goodsPieces;

    /**
     * the goods
     */
    @ManyToOne
    @JoinColumn(name = "cl_goods_nr")
    private Goods goodsNr;

    /**
     * user
     */
    @ManyToOne
    @JoinColumn(name = "cl_user_nr")
    private User userNr;

    /**
     * self-service-stand
     */
    @ManyToOne
    @JoinColumn(name = "cl_self_service_stand_nr")
    private SelfServiceStand selfServiceStandNr;

}