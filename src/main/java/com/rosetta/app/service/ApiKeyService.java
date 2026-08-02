package com.rosetta.app.service;

public interface ApiKeyService
{
    String generateApiKey(long userId) throws Exception;
}
