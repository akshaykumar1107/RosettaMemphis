package com.rosetta.app.ai;

public interface Translation
{
    String translate(String sourceText, String sourceLanguage, String translationLanguage, int plan);
}
