package com.victor.jbank.exception;

import com.victor.jbank.exception.dto.InvalidParamDTO;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(JBankException.class)
    public ProblemDetail handleJBankException(JBankException e) {
        return e.toProblemDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getFieldErrors();

        List<InvalidParamDTO> invalidParamDTOS =
                errors.stream().map(fd -> new InvalidParamDTO(fd.getField(), fd.getDefaultMessage())).toList();

        ProblemDetail problemDetail = ProblemDetail.forStatus(400);

        problemDetail.setTitle("Invalid Request Params");
        problemDetail.setDetail("There are invalid fields on the request");
        problemDetail.setProperty("invalid-fields", invalidParamDTOS);

        return problemDetail;
    }
}
