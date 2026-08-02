package com.rosetta.app.controller;

import com.rosetta.app.constant.APIResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/translate")
public class TranslateAPI implements TranslateController
{
    @Override
    @PostMapping
    public String translate(@RequestBody String requestBody) throws Exception
    {
        return APIResponse.SUCCESS.toResponseString("success");
    }
}
