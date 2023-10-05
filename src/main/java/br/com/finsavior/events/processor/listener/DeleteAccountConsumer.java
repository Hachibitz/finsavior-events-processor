package br.com.finsavior.events.processor.listener;

import br.com.finsavior.events.processor.service.UserService;
import br.com.finsavior.grpc.user.DeleteAccountRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(topics = "${topic.name}")
public class DeleteAccountConsumer {

    @Autowired
    UserService userService;

    @KafkaHandler
    public void deleteAccount (@NotNull DeleteAccountRequest message) throws Exception {
        log.info("Mensagem recebida: "+ message);
        boolean isDeleteSuccesful = userService.deleteAccount(message);

        if(isDeleteSuccesful){
            //ack.acknowledge();
        } else {
            throw new Exception("Falha ao executar a operação.");
        }
    }
}
