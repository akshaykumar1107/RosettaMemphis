package com.rosetta.app.kafka;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PayloadImpl implements Payload
{
    private String jsonString;

    @Override
    public void setJsonString(String jsonString)
    {
        this.jsonString = jsonString;
    }

    @Override
    public String getJsonString()
    {
        return jsonString;
    }
}
