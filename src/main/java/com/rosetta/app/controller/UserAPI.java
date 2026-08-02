package com.rosetta.app.controller;

import com.rosetta.app.constant.APIResponse;
import com.rosetta.app.constant.GeneralConstants;
import com.rosetta.app.service.ApiKeyService;
import com.rosetta.app.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserAPI implements UserController
{
    private final UserService userService;
    private final ApiKeyService apiKeyService;

    public UserAPI(UserService userService, ApiKeyService apiKeyService)
    {
        this.userService = userService;
        this.apiKeyService = apiKeyService;
    }

    @Override
    @PostMapping(value = "api/v1/users", produces = "application/json")
    public String addUser(@RequestBody String requestBody) throws Exception
    {
        JSONObject requestObj = new JSONObject(requestBody);
        long userId = userService.addNewUser(requestObj.getInt(GeneralConstants.PLAN));

        JSONArray apiKeys = new JSONArray();
        for(int i=0; i<5; i++)
        {
            apiKeys.put(apiKeyService.generateApiKey(userId));
        }

        return APIResponse.getSuccessJsonObj().put(GeneralConstants.API_KEYS, apiKeys).put(GeneralConstants.USER_ID, userId).toString();
    }

    @Override
    @PatchMapping(value = "api/v1/users/{userId}", produces = "application/json")
    public String modifyUser(@PathVariable("userId") long userId, @RequestBody String requestBody) throws Exception
    {
        JSONObject requestObj = new JSONObject(requestBody);
        userService.modifyUser(userId, requestObj.getInt(GeneralConstants.PLAN));
        return APIResponse.SUCCESS.toResponseString(String.format("User ID %s modified.", userId));
    }

    @Override
    @DeleteMapping(value = "api/v1/users/{userId}", produces = "application/json")
    public String deleteUser(@PathVariable("userId") long userId) throws Exception
    {
        userService.deleteUser(userId);
        return APIResponse.SUCCESS.toResponseString(String.format("User ID %s deleted.", userId));
    }
}
