package com.rosetta.app.cache;


import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class CacheUtil
{
    public static String generateKey(String sourceText, String sourceLanguage, String translationLanguage)
    {
        return Hashing.murmur3_128().hashString(sourceText+sourceLanguage+translationLanguage, StandardCharsets.UTF_8).toString();
    }
}
