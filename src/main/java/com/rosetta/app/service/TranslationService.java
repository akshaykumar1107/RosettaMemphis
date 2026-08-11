package com.rosetta.app.service;

import com.rosetta.app.entity.User;

public interface TranslationService
{
    long produceTranslation(String sourceText, String sourceLanguage, String translationLanguage, User user) throws Exception;
    String getTranslation(long translationId, long userId) throws Exception;
}
