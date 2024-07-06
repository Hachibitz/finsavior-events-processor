package br.com.finsavior.events.processor.service;

import br.com.finsavior.events.processor.model.dto.WebhookRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface WebhookService {

    void processWebhook(WebhookRequestDTO webhookRequestDTO);
}
