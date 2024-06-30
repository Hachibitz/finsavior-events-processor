package br.com.finsavior.events.processor.consumer;

import br.com.finsavior.events.processor.exception.DeleteAccountException;
import br.com.finsavior.events.processor.service.UserService;
import br.com.finsavior.grpc.user.DeleteAccountRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(topics = "${delete.account.topic.name}")
@RequiredArgsConstructor
public class DeleteAccountConsumer {

    private final UserService userService;

    @KafkaHandler
    public void deleteAccount (@NotNull DeleteAccountRequest message) throws Exception {
        log.info("Mensagem recebida do tópico: {}, mensagem: {}", "webhook-request", message);
        userService.deleteAccount(message);
    }
}
