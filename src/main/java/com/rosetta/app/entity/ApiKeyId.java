package com.rosetta.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ApiKeyId implements Serializable
{
    @Column(name = "user_id")//nullable = false not needed for PK.
    private Long userId;

    @Column(name = "api_key")
    private String apiKey;

    public ApiKeyId() {}

    public ApiKeyId(Long userId, String apiKey)
    {
        this.userId = userId;
        this.apiKey = apiKey;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getApiKey()
    {
        return apiKey;
    }

    public void setApiKey(String apiKey)
    {
        this.apiKey = apiKey;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o==this) return true;
        if(!(o instanceof ApiKeyId)) return false;

        ApiKeyId that = (ApiKeyId) o;

        return Objects.equals(userId, that.userId) && Objects.equals(apiKey, that.apiKey);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(userId, apiKey);
    }
}
