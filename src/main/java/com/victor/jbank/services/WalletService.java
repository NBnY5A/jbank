package com.victor.jbank.services;

import com.victor.jbank.controllers.dto.CreateWalletDTO;
import com.victor.jbank.entity.Wallet;
import com.victor.jbank.exception.DeleteWalletException;
import com.victor.jbank.exception.WalletDataAlreadyExistsException;
import com.victor.jbank.repositories.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(CreateWalletDTO dto) {
        if (walletRepository.findByCpfOrEmail(dto.cpf(), dto.email()).isPresent()) {
            throw new WalletDataAlreadyExistsException("Cpf or email already exists");
        }

        Wallet wallet = new Wallet();

        wallet.setBalance(BigDecimal.ZERO);
        wallet.setClientName(dto.name());
        wallet.setEmail(dto.email());
        wallet.setCpf(dto.cpf());

        return walletRepository.save(wallet);
    }

    public boolean deleteWallet(UUID walletId) {
        var wallet = walletRepository.findById(walletId);

        if (wallet.isPresent()) {
            if (wallet.get().getBalance().compareTo(BigDecimal.ZERO) != 0) {
                throw new DeleteWalletException("The wallet balance is not zero! Cannot delete wallet");
            }
            walletRepository.deleteById(walletId);
        }

        return wallet.isPresent();
    }
}
