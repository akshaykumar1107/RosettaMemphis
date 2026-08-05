package com.rosetta.app.service;

import com.rosetta.app.constant.ConfigConstants;
import com.rosetta.app.constant.GeneralConstants;
import com.rosetta.app.entity.TranslationHistory;
import com.rosetta.app.entity.User;
import com.rosetta.app.kafka.Payload;
import com.rosetta.app.kafka.Producer;
import com.rosetta.app.repository.TranslationHistoryRepository;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class TranslationProcessor implements TranslationService
{
    private final Producer producer;
    private final TranslationHistoryRepository translationHistoryRepository;
    private final ApplicationContext applicationContext;

    public TranslationProcessor(Producer producer, TranslationHistoryRepository translationHistoryRepository, ApplicationContext applicationContext)
    {
        this.producer = producer;
        this.translationHistoryRepository = translationHistoryRepository;
        this.applicationContext = applicationContext;
    }

    @Override
    public long produceTranslation(String sourceText, String sourceLanguage, String translationLanguage, User user) throws Exception
    {
        TranslationHistory translationHistory = new TranslationHistory(user, sourceText, sourceLanguage, "Loading", translationLanguage);
        translationHistoryRepository.save(translationHistory);

        long translationId = translationHistory.getTranslationId();

        int plan = user.getPlan();

        JSONObject jsonPayload = new JSONObject();
        jsonPayload.put(GeneralConstants.SOURCE_TEXT, sourceText);
        jsonPayload.put(GeneralConstants.SOURCE_LANGUAGE, sourceLanguage);
        jsonPayload.put(GeneralConstants.TRANSLATION_LANGUAGE, translationLanguage);
        jsonPayload.put(GeneralConstants.TRANSLATION_ID, translationId);
        jsonPayload.put(GeneralConstants.PLAN, plan);

        int partition = 0;

        if(plan == ConfigConstants.PAID_PLAN)
        {
            partition = System.currentTimeMillis()%2 == 0 ? 1 : 2;
        }

        Payload payload = applicationContext.getBean(Payload.class);
        payload.setString(jsonPayload.toString());

        producer.sendMessage(ConfigConstants.TOPIC, partition, payload);

        return translationId;
    }
}
