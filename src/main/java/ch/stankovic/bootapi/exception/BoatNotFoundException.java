package ch.stankovic.bootapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BoatNotFoundException extends RuntimeException {

    public BoatNotFoundException(String message) {
        super(message);
    }
}