package com.rosetta.app.kafka;


public interface Producer
{
    void sendMessage(String topic, int partition, Payload payload);
}
