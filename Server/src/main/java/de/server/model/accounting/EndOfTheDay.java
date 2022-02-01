package de.server.model.accounting;

import de.server.model.goods.Goods;
import de.server.model.userAndSelfServiceStand.SelfServiceStand;
import de.server.model.userAndSelfServiceStand.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Class represents the table "tb_end of_the_day".
 */
@Setter
@Getter
@Table(name = "`tb_end of_the_day`", indexes = {
        @Index(name = "cl_user_nr", columnList = "cl_user_nr"),
        @Index(name = "cl_goods_nr", columnList = "cl_goods_nr"),
        @Index(name = "cl_self_service_stand_nr", columnList = "cl_self_service_stand_nr")
})
@Entity
public class EndOfTheDay {
    /**
     * The end of the day id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cl_id_end", nullable = false)
    private Integer id;

    /**
     * the graduation date.
     */
    @Column(name = "cl_graduation_date", nullable = false)
    private LocalDate graduationDate;

    /**
     * the goods
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "cl_goods_nr", nullable = false)
    private Goods goodsNr;

    /**
     * the user
     */
    @ManyToOne
    @JoinColumn(name = "cl_user_nr")
    private User userNr;

    /**
     * the self-service-stand
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "cl_self_service_stand_nr", nullable = false)
    private SelfServiceStand selfServiceStandNr;

    /**
     * the graduation count
     */
    @Column(name = "cl_graduation_count")
    private Integer graduationCount;

    /**
     * the graduation sold
     */
    @Column(name = "cl_graduation_sold")
    private Integer graduationSold;

    /**
     * the graduation revenue
     */
    @Column(name = "cl_graduation_revenue")
    private Integer graduationRevenue;
}