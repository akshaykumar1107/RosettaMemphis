package com.rosetta.app.ai;

import com.rosetta.app.cache.CacheUtil;
import com.rosetta.app.entity.TranslationCache;
import com.rosetta.app.repository.TranslationCacheRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AITranslation implements TranslationService
{
    private final ChatClient ollamaChatClient;
    private final ChatClient geminiChatClient;
    private final TranslationCacheRepository translationCacheRepository;

    public AITranslation(
            @Qualifier("ollamaChatClient") ChatClient ollamaChatClient,
            @Qualifier("geminiChatClient") ChatClient geminiChatClient,
            TranslationCacheRepository translationCacheRepository
    )
    {
        this.ollamaChatClient = ollamaChatClient;
        this.geminiChatClient = geminiChatClient;
        this.translationCacheRepository = translationCacheRepository;
    }

    //db 0 is used by default.
    @Override
    @Cacheable(
            value = "translation",//namespace -> key in redis will be "translation::{key}"
//            key = "#parameterName", basic syntax for a simple one-parameter getter with the parameter value as key.
            key = "T(com.rosetta.app.cache.CacheUtil).generateKey(#sourceText, #sourceLanguage, #translationLanguage)",
            sync = true//If multiple threads fetch for the SAME key at the same time and if cache is not hit, all but one thread is blocked and fetch from source (method body execution) happens ONCE. After cache is primed, concurrency resumes and no more blocks take place.
    )
    public String translate(String sourceText, String sourceLanguage, String translationLanguage)
    {
        String key = CacheUtil.generateKey(sourceText, sourceLanguage, translationLanguage);

        Optional<TranslationCache> translationCacheOptional = translationCacheRepository.findById(key);

        String translatedText;

        if(translationCacheOptional.isPresent())
        {
            translatedText = translationCacheOptional.get().getTranslatedText();
        }
        else
        {
            translatedText = ollamaChatClient.prompt().user(String.format(
                    "Translate the source text from %s to %s. Reply only the translation in plain text.\nSource Text : %s",
                    sourceLanguage, translationLanguage, sourceText)
            ).call().content();

            translationCacheRepository.save(new TranslationCache(key, translatedText));//Use "new" to create entity objects.
        }

        return translatedText;
    }
}
