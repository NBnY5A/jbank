package com.victor.jbank.exception;

import org.springframework.http.ProblemDetail;

public class WalletNotExistsException extends JBankException {
    private final String detail;

    public WalletNotExistsException(String message) {
        super(message);
        this.detail = message;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(404);

        problemDetail.setTitle("Wallet not found");
        problemDetail.setDetail(detail);

        return problemDetail;
    }
}
