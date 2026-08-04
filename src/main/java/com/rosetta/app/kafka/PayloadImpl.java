package com.rosetta.app.kafka;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PayloadImpl implements Payload
{
    private String string;

    @Override
    public void setString(String string)
    {
        this.string = string;
    }

    @Override
    public String getString()
    {
        return string;
    }
}
