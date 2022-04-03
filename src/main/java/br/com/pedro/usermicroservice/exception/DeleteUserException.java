package br.com.pedro.usermicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DeleteUserException extends RuntimeException {
    public DeleteUserException(String message) {
        super(message);
    }
}
