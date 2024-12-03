package com.example.validation.domain.exceptions;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class BadRequestException extends RuntimeException {
    private final Map<String, List<String>> errors;

    public BadRequestException(Map<String, List<String>> errors) {
        super("Bad Request");
        this.errors = errors;
    }
}
