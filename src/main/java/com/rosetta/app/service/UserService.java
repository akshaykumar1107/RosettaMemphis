package com.rosetta.app.service;

public interface UserService
{
    long addNewUser(int plan) throws Exception;
    void modifyUser(long userId, int plan) throws Exception;
    void deleteUser(long userId) throws Exception;
}
