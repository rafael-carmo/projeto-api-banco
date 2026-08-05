package br.com.apibanco.mstransacoes.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.apibanco.mstransacoes.dtos.TransactionEventDTO;

@Service
public class TransactionEventProducer {
    private static final Logger log = LoggerFactory.getLogger(TransactionEventProducer.class);

    // Nome do tópico Kafka onde centralizaremos os eventos de transações
    private static final String TOPIC = "bank-transactions";

    // O Spring Boot injeta o KafkaTemplate configurado via application.yml
    private final KafkaTemplate<String, TransactionEventDTO> kafkaTemplate;

    public TransactionEventProducer(KafkaTemplate<String, TransactionEventDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransactionEvent(TransactionEventDTO transactionEvent) {
        log.info("Publicando evento de transação no Kafka: {}", transactionEvent);

        // Usamos accountSource (ou accountDestination) como CHAVE (Key) do Kafka.
        // O Kafka garante que mensagens com a mesma chave caiam na MESMA partição,
        // garantindo a ordem cronológica dos eventos de uma mesma conta.
        String partitionKey = transactionEvent.accountSource() != null ? transactionEvent.accountSource() : transactionEvent.accountDestination();
        
        // Envia o evento para o tópico Kafka
        kafkaTemplate.send(TOPIC, partitionKey, transactionEvent);
    }
}
