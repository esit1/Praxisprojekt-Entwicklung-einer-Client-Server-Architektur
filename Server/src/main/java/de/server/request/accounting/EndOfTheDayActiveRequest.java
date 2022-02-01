package de.server.request.accounting;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class EndOfTheDayActiveRequest {

    /**
     * The current date.
     */
    LocalDate graduationDate;

    /**
     * The self-Service-Stand name.
     */
    String selfServiceStandName;
}
