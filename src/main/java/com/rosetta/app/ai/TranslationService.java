package com.rosetta.app.ai;

public interface TranslationService
{
    String translate(String sourceText, String sourceLanguage, String translationLanguage);
}
