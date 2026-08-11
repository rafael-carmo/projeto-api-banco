package br.com.apibanco.mstransacoes.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    public static final String TOPIC_NAME = "bank-transactions";

    @Bean
    public NewTopic bankTransactionsTopic() {
        return TopicBuilder.name(TOPIC_NAME)
                .partitions(3) // Número de partições do tópico
                .replicas(1) // Número de réplicas do tópico
                .build();
    }
}
