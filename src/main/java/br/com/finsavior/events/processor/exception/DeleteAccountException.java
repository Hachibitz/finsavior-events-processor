package br.com.finsavior.events.processor.exception;

import lombok.Getter;

@Getter
public class DeleteAccountException extends RuntimeException {

    public DeleteAccountException(String message) { super(message); }

}
