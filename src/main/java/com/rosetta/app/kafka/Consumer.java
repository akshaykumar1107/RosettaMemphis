package com.rosetta.app.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface Consumer
{
    void listen(ConsumerRecord<String, Payload> record);
}
