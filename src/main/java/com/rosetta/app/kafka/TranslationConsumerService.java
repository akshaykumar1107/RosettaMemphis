package com.rosetta.app.kafka;

import com.rosetta.app.ai.Translator;
import com.rosetta.app.constant.ConfigConstants;
import com.rosetta.app.constant.GeneralConstants;
import com.rosetta.app.entity.TranslationHistory;
import com.rosetta.app.repository.TranslationHistoryRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TranslationConsumerService implements Consumer
{
    private static final Logger LOGGER = Logger.getLogger(TranslationConsumerService.class.getName());
    private final Translator translator;
    private final TranslationHistoryRepository translationHistoryRepository;

    public TranslationConsumerService(Translator translator, TranslationHistoryRepository translationHistoryRepository)
    {
        this.translator = translator;
        this.translationHistoryRepository = translationHistoryRepository;
    }

    @Override
    @KafkaListener(topics= ConfigConstants.TOPIC,//topic is a collection of similar messages. This particular listener is subscribed to the "translation" topic.
            groupId = "rosetta",//each group receives a copy of the produced message which will be processed independently.
            concurrency = ConfigConstants.PARTITIONS+"")//Number of threads to handle the messages belonging to partitions. A partition will always be processed by a single thread to maintain order.
    // If partitions > threads, the partitions are load balanced between the threads.
    // If threads > partitions, (threads - partitions) threads will be idle.
    // Ideally partitions = threads.
    public void listen(ConsumerRecord<String, Payload> record)//type Key, Value
    {
        JSONObject jsonPayload = new JSONObject(record.value().getString());
        int partition = record.partition();

        String translatedText = translator.translate(
                jsonPayload.getString(GeneralConstants.SOURCE_TEXT),
                jsonPayload.getString(GeneralConstants.SOURCE_LANGUAGE),
                jsonPayload.getString(GeneralConstants.TRANSLATION_LANGUAGE),
                jsonPayload.getInt(GeneralConstants.PLAN)
        );

        long translationId = jsonPayload.getLong(GeneralConstants.TRANSLATION_ID);
        Optional<TranslationHistory> translationHistoryOptional = translationHistoryRepository.findById(translationId);

        if(translationHistoryOptional.isPresent())
        {
            TranslationHistory translationHistory = translationHistoryOptional.get();
            translationHistory.setTranslatedText(translatedText);
            translationHistoryRepository.save(translationHistory);
        }
        else
        {
            LOGGER.log(Level.SEVERE, "KAFKA LOGS ::: Invalid Translation ID : {0}", translationId+"");
        }

        LOGGER.log(Level.SEVERE, "KAFKA LOGS ::: value : {0}, partition : {1}, thread : {2}", new Object[]{jsonPayload, partition, Thread.currentThread().getName()});
    }
}