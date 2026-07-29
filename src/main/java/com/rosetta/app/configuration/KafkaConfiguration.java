package com.rosetta.app.configuration;

import com.rosetta.app.constant.ConfigConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration
{
    @Bean
    public NewTopic translationTopic()
    {
        return TopicBuilder.name(ConfigConstants.TOPIC)
                .partitions(ConfigConstants.PARTITIONS)
                .replicas(1)//Increase for redundancy.
                .build();
    }
}
