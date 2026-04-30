package com.victor.jbank.controllers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record CreateWalletDTO(
        @NotBlank
        String name,

        @CPF
        @NotBlank
        String cpf,

        @Email
        @NotBlank
        String email
) {
}
