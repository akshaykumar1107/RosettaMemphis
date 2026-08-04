package com.rosetta.app.kafka;

import com.rosetta.app.constant.ConfigConstants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TranslationConsumerService implements Consumer
{
    private static Logger LOGGER = Logger.getLogger(TranslationConsumerService.class.getName());

    @Override
    @KafkaListener(topics= ConfigConstants.TOPIC,//topic is a collection of similar messages. This particular listener is subscribed to the "translation" topic.
            groupId = "rosetta",//each group receives a copy of the produced message which will be processed independently.
            concurrency = ConfigConstants.PARTITIONS+"")//Number of threads to handle the messages belonging to partitions. A partition will always be processed by a single thread to maintain order.
    // If partitions > threads, the partitions are load balanced between the threads.
    // If threads > partitions, (threads - partitions) threads will be idle.
    // Ideally partitions = threads.
    public void listen(ConsumerRecord<String, Payload> record)//type Key, Value
    {
        LOGGER.log(Level.SEVERE, "KAFKA LOGS ::: value : {0}, partition : {1}, thread : {2}", new Object[]{record.value().getString(), record.partition(), Thread.currentThread().getName()});
    }
}