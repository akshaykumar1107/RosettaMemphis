package com.rosetta.app.service;

import com.rosetta.app.constant.APIResponse;
import com.rosetta.app.constant.ConfigConstants;
import com.rosetta.app.constant.GeneralConstants;
import com.rosetta.app.entity.TranslationHistory;
import com.rosetta.app.entity.User;
import com.rosetta.app.exception.ResponseException;
import com.rosetta.app.kafka.Payload;
import com.rosetta.app.kafka.Producer;
import com.rosetta.app.repository.TranslationHistoryRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        TranslationHistory translationHistory = new TranslationHistory(user, sourceText, sourceLanguage, GeneralConstants.TRANSLATION_PLACEHOLDER, translationLanguage);
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

    @Override
    public String getTranslation(long translationId, long userId) throws Exception
    {
        Optional<TranslationHistory> translationHistoryOptional = translationHistoryRepository.findById(translationId);

        if(translationHistoryOptional.isPresent())
        {
            TranslationHistory translationHistory = translationHistoryOptional.get();

            if(translationHistory.getUser().getUserId() != userId)
            {
                throw new ResponseException(APIResponse.FETCH_TRANSLATION_PERMISSION_DENIED);
            }
            else
            {
                String translation = translationHistory.getTranslatedText();

                if(GeneralConstants.TRANSLATION_PLACEHOLDER.equals(translation))
                {
                    throw new ResponseException(APIResponse.TRANSLATION_NOT_YET_PROCESSED);
                }
                else
                {
                    return translation;
                }
            }

        }
        else
        {
            throw new ResponseException(APIResponse.INVALID_TRANSLATION_ID);
        }
    }

    @Override
    public JSONArray getTranslations(long userId, int pageNumber, int pageSize) throws Exception
    {
        if(pageSize > 50) throw new ResponseException(APIResponse.MAX_LIMIT_ERROR);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<TranslationHistory> translationHistories = translationHistoryRepository.findByUser_UserIdOrderByCreatedAtDesc(userId, pageable);

        if(translationHistories.isEmpty())
        {
            throw new ResponseException(APIResponse.RECORDS_NOT_FOUND);
        }
        else
        {
            JSONArray translationHistoriesArr = new JSONArray();

            translationHistories.stream().forEach(translationHistory ->
                    translationHistoriesArr.put(new JSONObject()
                            .put(GeneralConstants.TRANSLATION_ID, translationHistory.getTranslationId())
                            .put(GeneralConstants.SOURCE_LANGUAGE, translationHistory.getSourceLanguage())
                            .put(GeneralConstants.TRANSLATION_LANGUAGE, translationHistory.getTranslationLanguage())
                            .put(GeneralConstants.SOURCE_TEXT, translationHistory.getSourceText())
                            .put(GeneralConstants.TRANSLATED_TEXT, translationHistory.getTranslatedText())
                            .put(GeneralConstants.CREATED_AT, translationHistory.getCreatedAt()))

            );

            return translationHistoriesArr;
        }
    }
}
