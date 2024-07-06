package br.com.finsavior.events.processor.consumer;

import br.com.finsavior.events.processor.model.dto.WebhookRequestDTO;
import br.com.finsavior.events.processor.service.WebhookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebhookConsumer {

    private final WebhookService webhookService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = {"${webhook.request.topic.name}"}, containerFactory = "webhookKafkaListenerContainerFactory")
    public void onMessage(final String messageJson) throws JsonProcessingException {
        WebhookRequestDTO webhookRequestDTO = objectMapper.readValue(messageJson, WebhookRequestDTO.class);
        try {
            webhookService.processWebhook(webhookRequestDTO);
            log.info("Event for webhook request received successfully event: {}", webhookService);
        }catch (Exception e){
            log.error("Event failed to be consumed, event: {}, stackTrace: {}", webhookRequestDTO, e.getStackTrace());
        }
    }
}
