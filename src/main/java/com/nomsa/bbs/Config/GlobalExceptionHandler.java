package com.nomsa.bbs.Config;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;

@RestControllerAdvice
@RestController
public class GlobalExceptionHandler implements ErrorController {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void baseException (Exception e) {e.printStackTrace();}

    @ExceptionHandler(value = {BadRequestException.class, MissingServletRequestParameterException.class, DuplicateKeyException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void badRequestException (Exception exception) {exception.printStackTrace();}

    @ExceptionHandler(value = NotAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void unAuthorizedException (Exception exception) {exception.printStackTrace();}

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void notFoundException () {}

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void methodNotAllowedException () {}

    @Override
    public String getErrorPath() { return null; }
}