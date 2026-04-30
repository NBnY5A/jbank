package com.victor.jbank.controllers.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DepositDTO(
        @NotNull
        @DecimalMin("5.00")
        BigDecimal value
) {
}
