package com.rosetta.app.controller;

import com.rosetta.app.constant.APIResponse;
import com.rosetta.app.constant.GeneralConstants;
import com.rosetta.app.service.UserService;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserAPI implements UserController
{
    private final UserService userService;

    public UserAPI(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    @PostMapping(value = "api/v1/users", produces = "application/json")
    public String addUser(@RequestBody String requestBody) throws Exception
    {
        JSONObject requestObj = new JSONObject(requestBody);
        long userId = userService.addNewUser(requestObj.getInt(GeneralConstants.PLAN));
        return APIResponse.SUCCESS.toResponseString(String.format("User ID %s created.", userId));
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
