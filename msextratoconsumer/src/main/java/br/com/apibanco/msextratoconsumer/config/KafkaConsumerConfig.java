package br.com.apibanco.msextratoconsumer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConsumerConfig {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    /**
     * Configura o comportamento do Kafka quando um erro acontece no Listener.
     */
    @Bean
    public CommonErrorHandler errorHandler(KafkaTemplate<Object, Object> template) {
        // Se falhar, joga a mensagem para o tópico original com o sufixo ".DLQ"
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(template);

        // Define a estratégia de tentativa: Tenta reprocessar a mensagem a cada 2
        // segundos (2000ms),
        // permitindo no máximo 3 tentativas antes de desistir e mandar para a DLQ.
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, new FixedBackOff(2000L, 3));

        // Loga no console o motivo exato de termos descartado a mensagem
        // errorHandler.setLogLevel(KafkaLog -> log.error("Mensagem enviada para a DLQ
        // após esgotar as tentativas."));
        errorHandler.setLogLevel(KafkaException.Level.ERROR);

        return errorHandler;
    }
}
