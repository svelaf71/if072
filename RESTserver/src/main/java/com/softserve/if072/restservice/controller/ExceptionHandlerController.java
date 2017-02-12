package com.softserve.if072.restservice.controller;

import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The ExceptionHandlerController class is used to provide methods that handler common exceptions
 *
 * @author Igor Kryviuk
 */
@RestController
public class ExceptionHandlerController {
    private static final Logger LOGGER = LogManager.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String dataNotFound(DataNotFoundException e) {
        LOGGER.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String dataAccessException(DataAccessException e) {
        LOGGER.error(e.getMessage());
        return e.getMessage();
    }
}
