package com.rosetta.app.service;

import com.rosetta.app.entity.User;
import org.json.JSONArray;

public interface TranslationService
{
    long produceTranslation(String sourceText, String sourceLanguage, String translationLanguage, User user) throws Exception;
    String getTranslation(long translationId, long userId) throws Exception;
    JSONArray getTranslations(long userId, int pageNumber, int pageSize) throws Exception;
}
