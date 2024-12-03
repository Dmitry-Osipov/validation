package com.example.validation.error.dto;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
    private String error;
    private String message;
    private Integer status;
    private String path;
    private ZonedDateTime timestamp;
    private Map<String, List<String>> details;
}