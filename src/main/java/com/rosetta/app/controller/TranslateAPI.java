package com.rosetta.app.controller;

import com.rosetta.app.constant.APIResponse;
import com.rosetta.app.constant.GeneralConstants;
import com.rosetta.app.entity.User;
import com.rosetta.app.kafka.Producer;
import com.rosetta.app.service.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/translate", produces = "application/json")
public class TranslateAPI implements TranslateController
{
    private final TranslationService translationService;
    private final Producer producer;
    private final ApplicationContext applicationContext;

    public TranslateAPI(TranslationService translationService, Producer producer, ApplicationContext applicationContext) throws Exception
    {
        this.translationService = translationService;
        this.producer = producer;
        this.applicationContext = applicationContext;
    }

    @Override
    @PostMapping
    public String translate(HttpServletRequest request, @RequestBody String requestBody) throws Exception
    {
        JSONObject requestObj = new JSONObject(requestBody);
        long translationId = translationService.produceTranslation(requestObj.getString(GeneralConstants.SOURCE_TEXT), requestObj.getString(GeneralConstants.SOURCE_LANGUAGE), requestObj.getString(GeneralConstants.TRANSLATION_LANGUAGE), (User) request.getAttribute(GeneralConstants.USER));
        return APIResponse.getSuccessJsonObj().put(GeneralConstants.TRANSLATION_ID, translationId).toString();
    }
}
