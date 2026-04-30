package com.victor.jbank.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

public class WalletDataAlreadyExistsException extends JBankException {
    private final String message;

    public WalletDataAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatusCode.valueOf(442));

        problemDetail.setTitle("Wallet data already exists");
        problemDetail.setDetail(message);

        return problemDetail;
    }
}
