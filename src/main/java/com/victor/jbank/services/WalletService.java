package com.victor.jbank.services;

import com.victor.jbank.controllers.dto.CreateWalletDTO;
import com.victor.jbank.controllers.dto.DepositDTO;
import com.victor.jbank.entity.Deposit;
import com.victor.jbank.entity.Wallet;
import com.victor.jbank.exception.DeleteWalletException;
import com.victor.jbank.exception.WalletDataAlreadyExistsException;
import com.victor.jbank.exception.WalletNotExistsException;
import com.victor.jbank.repositories.DepositRepository;
import com.victor.jbank.repositories.WalletRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final DepositRepository depositRepository;

    public WalletService(WalletRepository walletRepository, DepositRepository depositRepository) {
        this.walletRepository = walletRepository;
        this.depositRepository = depositRepository;
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

    @Transactional
    public void deposit(UUID walletId, @Valid DepositDTO depositDTO, String ipAddress) {
        var wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotExistsException("There is no wallet with this id"));

        Deposit deposit = new Deposit();

        deposit.setWallet(wallet);
        deposit.setQuantity(depositDTO.value());
        deposit.setRealizedAt(LocalDateTime.now());
        deposit.setIpAddress(ipAddress);

        depositRepository.save(deposit);

        wallet.setBalance(wallet.getBalance().add(depositDTO.value()));

        walletRepository.save(wallet);
    }
}
