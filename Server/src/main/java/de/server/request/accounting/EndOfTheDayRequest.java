package de.server.request.accounting;

import lombok.Getter;

import java.time.LocalDate;

/**
 * This class contains the inputs for the "EndOfTheDay" interface.
 */
@Getter
public class EndOfTheDayRequest {

    /**
     * The current date.
     */
    LocalDate graduationDate;

    /**
     * The name of the goods.
     */
    int goodsID;

    /**
     * The user name.
     */
    String userName;

    /**
     * The self-Service-Stand name.
     */
    String selfServiceStandName;

    /**
     * The number of goods counted.
     */
    int graduationCount;

}
