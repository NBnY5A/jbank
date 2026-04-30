package com.victor.jbank.exception.dto;

public record InvalidParamDTO(
        String field,
        String reason) {
}
