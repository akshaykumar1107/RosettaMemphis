package com.rosetta.app.controller;

import jakarta.servlet.http.HttpServletRequest;

public interface TranslateController
{
    String translate(HttpServletRequest request, String requestBody) throws Exception;
    String getTranslation(HttpServletRequest request, long translationId) throws Exception;
}
