package com.example.validation.error.controller;

import com.example.validation.domain.exceptions.BadRequestException;
import com.example.validation.error.dto.ErrorDto;
import com.example.validation.error.dto.ErrorType;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, List<String>> errors = getAllErrors(ex);
        return ErrorDto.builder()
                .error(ErrorType.VALIDATE)
                .message("Validation Error")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(now())
                .path(request.getDescription(false))
                .details(errors)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NullPointerException.class})
    public ErrorDto handleNullPointerException(NullPointerException ex, WebRequest request) {
        return ErrorDto.builder()
                .error(ErrorType.ERROR)
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(now())
                .path(request.getDescription(false))
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    public ErrorDto handleBadRequestException(BadRequestException ex, WebRequest request) {
        return ErrorDto.builder()
                .error(ErrorType.ERROR)
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(now())
                .path(request.getDescription(false))
                .details(ex.getErrors())
                .build();
    }

    private static ZonedDateTime now() {
        return ZonedDateTime.now(ZoneOffset.UTC);
    }

    private static Map<String, List<String>> getAllErrors(MethodArgumentNotValidException ex) {
        Map<String, List<String>> fieldErrors = getFieldsErrors(ex);
        Map<String, List<String>> globalErrors = getGlobalErrors(ex);
        Map<String, List<String>> errors = HashMap.newHashMap((fieldErrors.size() + globalErrors.size()));
        errors.putAll(fieldErrors);
        errors.putAll(globalErrors);
        return errors;
    }

    private static Map<String, List<String>> getFieldsErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));
    }

    private static Map<String, List<String>> getGlobalErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getGlobalErrors().stream()
                .flatMap(objectError -> {
                    Object[] args = objectError.getArguments();
                    if (args != null) {
                        List<String> fields = Arrays.stream(args)
                                .filter(arg -> !DefaultMessageSourceResolvable.class.isInstance(arg))
                                .map(Object::toString)
                                .toList();
                        return fields.stream()
                                .map(field -> Map.entry(field, objectError.getDefaultMessage()));
                    }
                    return Stream.of(Map.entry(objectError.getObjectName(), objectError.getDefaultMessage()));
                })
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));
    }

}
