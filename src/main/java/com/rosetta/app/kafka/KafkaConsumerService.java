package com.rosetta.app.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService implements Consumer
{
    @Override
    @KafkaListener(topics="translation",//topic is a collection of similar messages. This particular listener is subscribed to the "translation" topic.
            groupId = "rosetta",//each group receives a copy of the produced message which will be processed independently.
            concurrency = "3")//Number of threads to handle the messages belonging to partitions. A partition will always be processed by a single thread to maintain order.
    // If partitions > threads, the partitions are load balanced between the threads.
    // If threads > partitions, (threads - partitions) threads will be idle.
    // Ideally partitions = threads.
    public void listen(Payload payLoad)
    {

    }
}
