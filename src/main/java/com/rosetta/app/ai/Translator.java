package com.rosetta.app.ai;

public interface Translator
{
    String translate(String sourceText, String sourceLanguage, String translationLanguage, int plan);
}
