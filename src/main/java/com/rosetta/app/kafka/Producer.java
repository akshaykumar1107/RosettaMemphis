package com.rosetta.app.kafka;


public interface Producer
{
    void sendMessage(String topic, String key, Payload payload);
}
