package com.rosetta.app.exception;

import com.rosetta.app.constant.APIResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class ErrorHandler
{
    private static final Logger LOGGER = Logger.getLogger(ErrorHandler.class.getName());

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity handleException(Exception e)
    {
        LOGGER.log(Level.SEVERE, e.getMessage(), e);

        APIResponse apiResponse = e instanceof ResponseException ? ((ResponseException) e).getApiResponse() : APIResponse.GENERIC_ERROR;

        return ResponseEntity
                .status(apiResponse.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(apiResponse.toResponseString());
    }
}
