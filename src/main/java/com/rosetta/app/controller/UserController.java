package com.rosetta.app.controller;

public interface UserController
{
    String addUser(String requestBody) throws Exception;
    String modifyUser(long userId, String requestBody) throws Exception;
    String deleteUser(long userId) throws Exception;
}
