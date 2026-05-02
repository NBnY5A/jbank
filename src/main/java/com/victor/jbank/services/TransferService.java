package com.victor.jbank.services;

import com.victor.jbank.controllers.dto.CreateTransferDTO;
import com.victor.jbank.entity.Transfer;
import com.victor.jbank.exception.TransferException;
import com.victor.jbank.exception.WalletNotExistsException;
import com.victor.jbank.repositories.TransferRepository;
import com.victor.jbank.repositories.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransferService {
    private final TransferRepository transferRepository;
    private final WalletRepository walletRepository;

    public TransferService(TransferRepository transferRepository, WalletRepository walletRepository) {
        this.transferRepository = transferRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public void transfer(CreateTransferDTO dto) {
        var walletReceiver = walletRepository.findById(dto.receiver())
                .orElseThrow(() -> new WalletNotExistsException("Receiver does not exists!"));

        var walletSender = walletRepository.findById(dto.sender())
                .orElseThrow(() -> new WalletNotExistsException("Sender does not exists!"));

        if (walletSender.getBalance().compareTo(dto.quantity()) < 0) {
            throw new TransferException("Insufficient balance to continues this operation");
        }

        var transfer = new Transfer();

        transfer.setSender(walletSender);
        transfer.setReceiver(walletReceiver);

        walletSender.setBalance(walletSender.getBalance().subtract(dto.quantity()));
        walletReceiver.setBalance(walletReceiver.getBalance().add(dto.quantity()));

        transfer.setQuantity(dto.quantity());

        transfer.setRealizedAt(LocalDateTime.now());

        walletRepository.save(walletSender);
        walletRepository.save(walletReceiver);
        transferRepository.save(transfer);
    }
}
