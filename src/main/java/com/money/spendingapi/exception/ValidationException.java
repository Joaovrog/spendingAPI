package com.money.spendingapi.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class ValidationException extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessageForUser = messageSource.getMessage("invalid.message", null, LocaleContextHolder.getLocale());
        String errorMessageForDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString(); // differentiating "pure" exceptions (that haven't the 'cause' property) from exceptions that have it.
        List<ErrorMessageFor> errors = Arrays.asList(new ErrorMessageFor(errorMessageForUser, errorMessageForDev));
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorMessageFor> errors = createErrorList(ex.getBindingResult());
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler( { EmptyResultDataAccessException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(RuntimeException ex, WebRequest request) {
        String errorMessageForUser = messageSource.getMessage("resource.not-found", null, LocaleContextHolder.getLocale());
        String errorMessageForDev = ex.toString();
        List<ErrorMessageFor> errors = Arrays.asList(new ErrorMessageFor(errorMessageForUser, errorMessageForDev));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler( { DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String errorMessageForUser = messageSource.getMessage("resource.forbidden-operation ", null, LocaleContextHolder.getLocale());
        String errorMessageForDev = ExceptionUtils.getRootCauseMessage(ex); // Using CommonsLang to retrieve the error message and help developers when they receive errorMessageForDev.
        List<ErrorMessageFor> errors = Arrays.asList(new ErrorMessageFor(errorMessageForUser, errorMessageForDev));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    private List<ErrorMessageFor> createErrorList(BindingResult bindingResult) {
        List<ErrorMessageFor> list = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String messageUser = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String messageDev = fieldError.toString();
            list.add(new ErrorMessageFor(messageUser, messageDev));
        }

        return list;

    }



    public static class ErrorMessageFor {

        private String errorMessageForUser;
        private String errorMessageForDev;

        public ErrorMessageFor(String errorMessageForUser, String errorMessageForDev) {
            this.errorMessageForUser = errorMessageForUser;
            this.errorMessageForDev = errorMessageForDev;
        }

        public String getErrorMessageForUser() {
            return errorMessageForUser;
        }

        public String getErrorMessageForDev() {
            return errorMessageForDev;
        }
    }
}
