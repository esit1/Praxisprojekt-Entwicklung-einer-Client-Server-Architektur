package de.server.response.accounting;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * The EndOfTheDayResponse.
 */
@Getter
@Setter
public class EndOfTheDayResponse {

    LocalDate graduationDate;
    String goodsName;
    String goodsUnit;
    int graduationCount;
    int graduationSold;
    int graduationRevenue;
    double goodsPrice;

    public EndOfTheDayResponse(LocalDate graduationDate, String goodsName, String goodsUnit, int graduationCount, int graduationSold, int graduationRevenue, double goodsPrice) {
        this.graduationDate = graduationDate;
        this.goodsName = goodsName;
        this.goodsUnit = goodsUnit;
        this.graduationCount = graduationCount;
        this.graduationSold = graduationSold;
        this.graduationRevenue = graduationRevenue;
        this.goodsPrice = goodsPrice;
    }
}
