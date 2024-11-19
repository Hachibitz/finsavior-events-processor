package br.com.finsavior.events.processor.model.dto;

import lombok.Data;

@Data
public class DeleteAccountRequestDTO {
    private String username;
    private String password;
    private boolean confirmation;
}
