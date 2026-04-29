package com.victor.jbank.services;

import com.victor.jbank.controllers.dto.CreateWalletDTO;
import com.victor.jbank.entity.Wallet;
import com.victor.jbank.exception.WalletDataAlreadyExists;
import com.victor.jbank.repositories.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(CreateWalletDTO dto) {
        if (walletRepository.findByCpfOrEmail(dto.cpf(), dto.email()).isPresent()) {
            throw new WalletDataAlreadyExists("Cpf or email already exists");
        }

        Wallet wallet = new Wallet();

        wallet.setBalance(BigDecimal.ZERO);
        wallet.setClientName(dto.name());
        wallet.setEmail(dto.email());
        wallet.setCpf(dto.cpf());

        return walletRepository.save(wallet);
    }
}
