package com.rosetta.app.entity;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("translation_cache")
public class TranslationCache
{
    @PrimaryKey
    @Column("cache_key")
    private String cacheKey;

    @Column("translated_text")
    private String translatedText;

    public TranslationCache(String cacheKey, String translatedText)
    {
        this.cacheKey = cacheKey;
        this.translatedText = translatedText;
    }

    public void setCacheKey(String cacheKey)
    {
        this.cacheKey = cacheKey;
    }

    public String getCacheKey()
    {
        return cacheKey;
    }

    public void setTranslatedText(String translatedText)
    {
        this.translatedText = translatedText;
    }

    public String getTranslatedText()
    {
        return translatedText;
    }
}
