package com.victor.jbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class TransferException extends JBankException {
    private final String details;

    public TransferException(String message) {
        super(message);
        this.details = message;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(422);

        problemDetail.setTitle("Insufficient balance to realize transfer");
        problemDetail.setDetail(details);

        return problemDetail;
    }
}
