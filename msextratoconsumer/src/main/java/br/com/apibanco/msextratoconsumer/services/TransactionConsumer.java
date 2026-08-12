package br.com.apibanco.msextratoconsumer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import br.com.apibanco.msextratoconsumer.dtos.TransactionEventDTO;
import br.com.apibanco.msextratoconsumer.entities.TransactionEntity;
import br.com.apibanco.msextratoconsumer.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionConsumer {
    private static final Logger log = LoggerFactory.getLogger(TransactionConsumer.class);
    private final TransactionRepository repository;

    @Value("${app.spring.kafka.topic.transactions}")
    private String topicName;

    @Value("${app.spring.kafka.consumer.group-id}")
    private String groupId;

    /**
     * O Spring injeta o objeto deserializado automaticamente baseado na nossa
     * config do yml.
     */
    // @KafkaListener(topics = "${topicName}", groupId = "${groupId}")
    @KafkaListener(topics = "bank-transactions", groupId = "cassandra-extrato-group")
    public void consume(TransactionEventDTO event) {
        log.info("Processando evento recebido do Kafka: {}", event.transactionId());

        try {
            // Regra 1: Se houve conta de origem (Saque ou Transferência enviada)
            if (event.accountSource() != null) {
                log.info("Registrando débito para a conta: {}", event.accountSource());
                saveRecord(event, event.accountSource());
            }
            // Regra 2: Se houve conta de destino (Depósito ou Transferência recebida)
            if (event.accountDestination() != null && !event.type().equals("WITHDRAW")) {
                log.info("Registrando crédito para a conta: {}", event.accountDestination());
                saveRecord(event, event.accountDestination());
            }
        } catch (Exception e) {
            // Em cenários de produção, enviaríamos para uma DLQ (Dead Letter Queue) para
            // não travar a fila
            log.error("Falha catastrófica ao indexar extrato no Cassandra: ", e);
        }
    }

    private void saveRecord(TransactionEventDTO event, String targetAccount) {
        TransactionEntity entity = new TransactionEntity(
                targetAccount, // Chave de partição: o extrato pertence a esta conta
                event.timestamp(), // Chave de ordenação
                event.transactionId(),
                event.accountDestination(),
                event.amount(),
                event.type(),
                event.status());

        repository.save(entity);
    }
}
