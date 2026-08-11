package br.com.apibanco.msextratoconsumer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Table("transactions_by_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    // Chave de Partição: Define em qual nó do cluster o dado vai morar
    @PrimaryKeyColumn(name = "acount_id", type = PrimaryKeyType.PARTITIONED)
    private String accountId;

    // Chave de Ordenação (Clustering Key): Garante a ordenação decrescente física
    // no disco
    @PrimaryKeyColumn(name = "transaction_time", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Instant transactionTime;

    @Column("transaction_id")
    private UUID transactionId;

    @Column("account_destination")
    private String accountDestination;

    @Column("amount")
    private BigDecimal amount;

    @Column("type")
    private String type;

    @Column("status")
    private String status;
}
