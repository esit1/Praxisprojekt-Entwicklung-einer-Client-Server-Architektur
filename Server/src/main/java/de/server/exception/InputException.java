package de.server.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class contains the exception output.
 */
public class InputException extends Exception {
    /**
     * Logger of the UserController class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(InputException.class);

    /**
     * Message that is displayed if the field is empty or does not exist
     */
    private static final String ERROR_MESSAGE = "No entry, (%s) the field is empty or does not exist. ";

    public InputException(String emptyField) {
        super(String.format(ERROR_MESSAGE, emptyField));

        LOGGER.info(ERROR_MESSAGE);
    }

}

