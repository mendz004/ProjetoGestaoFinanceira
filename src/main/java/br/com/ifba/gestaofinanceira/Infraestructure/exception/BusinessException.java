package br.com.ifba.gestaofinanceira.Infraestructure.exception;

import org.springframework.data.repository.query.Param;

import java.io.Serial;

public class BusinessException extends RuntimeException {

    @Serial
    private static final Long serialVersionUID = 1L;

    public BusinessException() {
        super();

    }

    // Construtor que recebe uma mensagem de erro.
    public BusinessException(final String message) {
        super(message);
    }

    // Construtor que recebe a causa do erro.
    public BusinessException(final Throwable cause) {
        super(cause);
    }

    // recebe a mensagem e a causa do erro.
    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
