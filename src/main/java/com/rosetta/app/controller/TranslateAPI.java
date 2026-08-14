package com.rosetta.app.controller;

import com.rosetta.app.constant.APIResponse;
import com.rosetta.app.constant.GeneralConstants;
import com.rosetta.app.entity.User;
import com.rosetta.app.kafka.Producer;
import com.rosetta.app.service.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json")
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
    @PostMapping("/api/v1/translate")
    public String translate(HttpServletRequest request, @RequestBody String requestBody) throws Exception
    {
        JSONObject requestObj = new JSONObject(requestBody);
        long translationId = translationService.produceTranslation(requestObj.getString(GeneralConstants.SOURCE_TEXT), requestObj.getString(GeneralConstants.SOURCE_LANGUAGE), requestObj.getString(GeneralConstants.TRANSLATION_LANGUAGE), (User) request.getAttribute(GeneralConstants.USER));
        return APIResponse.getSuccessJsonObj().put(GeneralConstants.TRANSLATION_ID, translationId).toString();
    }

    @Override
    @GetMapping("/api/v1/translate/{translationId}")
    public String getTranslation(HttpServletRequest request, @PathVariable(value = "translationId") long translationId) throws Exception
    {
        return APIResponse.getSuccessJsonObj().put(GeneralConstants.TRANSLATED_TEXT, translationService.getTranslation(translationId, ((User) request.getAttribute(GeneralConstants.USER)).getUserId())).toString();
    }

    @Override
    @GetMapping("/api/v1/translate")
    public String getTranslations(HttpServletRequest request, @RequestParam(value = GeneralConstants.PAGE_NUMBER) int pageNumber, @RequestParam(value = GeneralConstants.PAGE_SIZE) int pageSize) throws Exception
    {
        return APIResponse.getSuccessJsonObj().put("records", translationService.getTranslations(((User) request.getAttribute(GeneralConstants.USER)).getUserId(), pageNumber, pageSize)).toString();
    }
}
