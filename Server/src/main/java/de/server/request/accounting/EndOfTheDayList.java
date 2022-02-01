package de.server.request.accounting;

import lombok.Getter;

/**
 * Contains the request end of the day list with all entries.
 */
@Getter
public class EndOfTheDayList {

    //The self-service-stand name.
    String SelfServiceStandName;

    // The user name.
    String UserName;
}
