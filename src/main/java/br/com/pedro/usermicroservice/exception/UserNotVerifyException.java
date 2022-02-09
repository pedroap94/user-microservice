package br.com.pedro.usermicroservice.exception;

public class UserNotVerifyException extends RuntimeException {
    public UserNotVerifyException(String message) {
        super(message);
    }
}
