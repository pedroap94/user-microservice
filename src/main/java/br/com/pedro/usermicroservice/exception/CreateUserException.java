package br.com.pedro.usermicroservice.exception;

public class CreateUserException extends RuntimeException {
    public CreateUserException(String message) {
        super(message);
    }
}
