package br.com.pedro.usermicroservice.exception;

public class CreateCartException extends RuntimeException {
    public CreateCartException(String message) {
        super(message);
    }
}
