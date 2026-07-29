package com.rosetta.app.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class KafkaProducerService implements Producer
{
    private final KafkaTemplate<String, Payload> kafkaTemplate;//type Key, Value
    private static final Logger LOGGER = Logger.getLogger(KafkaProducerService.class.getName());

    //@Autowired is implicit for single constructor.
    public KafkaProducerService(KafkaTemplate<String, Payload> kafkaTemplate)
    {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Producer publishes the message to a topic.
     * @param topic The topic to publish the message to.
     * @param partition All messages in a particular partition are processed sequentially.
     * @param payload A POJO
     */
    @Override
    public void sendMessage(String topic, int partition, Payload payload)
    {
        kafkaTemplate.send(topic, partition, null, payload)//key determines which partition a message goes to. Redundant here as partition is manually assigned.
                .whenComplete((result, ex) -> {
                    if(ex != null)
                    {
                        LOGGER.log(Level.SEVERE, ex.getMessage());
                    }
                    else
                    {
                        LOGGER.log(Level.SEVERE, result.getRecordMetadata().toString());
                    }
                });
    }
}
