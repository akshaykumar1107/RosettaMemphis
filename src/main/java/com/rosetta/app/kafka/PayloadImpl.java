package com.rosetta.app.kafka;

public class PayloadImpl implements Payload
{
    private String jsonString = "{}";

    @Override
    public String getJsonString()
    {
        return jsonString;
    }
}
