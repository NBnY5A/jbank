package com.victor.jbank.controllers;

import com.victor.jbank.controllers.dto.CreateWalletDTO;
import com.victor.jbank.services.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/wallets")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity<Void> createWallet(@RequestBody @Valid CreateWalletDTO dto) {
        var wallet = walletService.createWallet(dto);
        return ResponseEntity.created(URI.create("/wallet/" + wallet.getId().toString())).build();
    }
}
