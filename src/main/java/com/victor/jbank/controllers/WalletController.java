package com.victor.jbank.controllers;

import com.victor.jbank.controllers.dto.CreateWalletDTO;
import com.victor.jbank.controllers.dto.DepositDTO;
import com.victor.jbank.services.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

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

    @DeleteMapping("/{walletId}")
    public ResponseEntity<Void> deleteWallet(@PathVariable(name = "walletId") UUID walletId) {
        boolean isDeleted = walletService.deleteWallet(walletId);

        return (isDeleted)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/{walletId}/deposit")
    public ResponseEntity<Void> depositBalance(
            @PathVariable(name = "walletId") UUID walletId,
            @RequestBody @Valid DepositDTO depositDTO,
            HttpServletRequest request
    ) {
        walletService.deposit(walletId, depositDTO, request.getAttribute("x-user-ip").toString());
        return ResponseEntity.ok().build();
    }
}
