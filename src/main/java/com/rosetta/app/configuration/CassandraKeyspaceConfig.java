package com.rosetta.app.configuration;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.cassandra.autoconfigure.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
public class CassandraKeyspaceConfig
{
    @Bean
    public CqlSessionBuilderCustomizer keyspaceCreationCustomizer(
            @Value("${spring.cassandra.contact-points}") String contactPoints,//values injected from application.properties. Default values if not exists in application.properties.
            @Value("${spring.cassandra.port}") int port,
            @Value("${spring.cassandra.local-datacenter}") String localDatacenter,
            @Value("${spring.cassandra.keyspace-name}") String keyspace
    )
    {
        try (CqlSession bootstrapSession = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(contactPoints, port))
                .withLocalDatacenter(localDatacenter)
                .build())
        {
            bootstrapSession.execute(
                    SchemaBuilder.createKeyspace(keyspace)//keyspace creation if not exists.
                            .ifNotExists()
                            .withSimpleStrategy(1)//replication factor (number of copies) for redundancy.
                            .build()
            );
        }

        return builder -> {};//Keyspace creation is complete at this point. Return a dummy functional interface implementation to satisfy the return type.
    }
}
