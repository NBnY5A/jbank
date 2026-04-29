package com.victor.jbank.controllers.dto;

public record CreateWalletDTO(
        String name,
        String cpf,
        String email
) {
}
