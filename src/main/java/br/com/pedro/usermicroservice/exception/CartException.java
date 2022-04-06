package br.com.pedro.usermicroservice.exception;

public class CartException extends RuntimeException {
    public CartException(String message) {
        super(message);
    }
}
