package com.rosetta.app.service;

import com.rosetta.app.constant.APIResponse;
import com.rosetta.app.entity.ApiKey;
import com.rosetta.app.entity.User;
import com.rosetta.app.exception.ResponseException;
import com.rosetta.app.repository.ApiKeyRepository;
import com.rosetta.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ApiKeyProcessor implements ApiKeyService
{
    private final ApiKeyRepository apiKeyRepository;
    private final UserRepository userRepository;

    public ApiKeyProcessor(ApiKeyRepository apiKeyRepository, UserRepository userRepository)
    {
        this.apiKeyRepository = apiKeyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String generateApiKey(long userId) throws Exception
    {
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isPresent())
        {
            String apiKey = UUID.randomUUID().toString();
            apiKeyRepository.save(new ApiKey(userOptional.get(), apiKey));
            return apiKey;
        }
        else
        {
            throw new ResponseException(APIResponse.INVALID_USER_ID);
        }
    }
}
