package com.victor.jbank.exception;

import org.springframework.http.ProblemDetail;

public class DeleteWalletException extends JBankException {
    private final String detail;

    public DeleteWalletException(String message) {
        super(message);
        this.detail = message;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(422);

        problemDetail.setTitle("You cannot delete this wallet");
        problemDetail.setDetail(detail);

        return problemDetail;
    }
}
