package br.com.finsavior.events.processor.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private HttpStatus status;

    public BusinessException(String message) { super(message); }

    public BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
