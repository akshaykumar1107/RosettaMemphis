package com.rosetta.app.service;

import com.rosetta.app.constant.APIResponse;
import com.rosetta.app.entity.User;
import com.rosetta.app.exception.ResponseException;
import com.rosetta.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProcessor implements UserService
{
    private final UserRepository userRepository;

    public UserProcessor(UserRepository userRepository) throws Exception
    {
        this.userRepository = userRepository;
    }

    @Override
    public long addNewUser(int plan)
    {
        User user = new User(plan);
        userRepository.save(user);
        return user.getUserId();
    }

    @Override
    public void modifyUser(long userId, int plan) throws Exception
    {
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isPresent())
        {
            User user = userOptional.get();
            user.setPlan(plan);
            userRepository.save(user);
        }
        else
        {
            throw new ResponseException(APIResponse.INVALID_USER_ID);
        }
    }

    @Override
    public void deleteUser(long userId) throws Exception
    {
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isPresent())
        {
            userRepository.deleteById(userId);
        }
        else
        {
            throw new ResponseException(APIResponse.INVALID_USER_ID);
        }
    }
}
