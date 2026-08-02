package com.rosetta.app.interceptor;

import org.springframework.beans.factory.annotation.Value;
import com.rosetta.app.constant.APIResponse;
import com.rosetta.app.constant.GeneralConstants;
import com.rosetta.app.entity.ApiKey;
import com.rosetta.app.exception.ResponseException;
import com.rosetta.app.repository.ApiKeyRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor
{
    private final ApiKeyRepository apiKeyRepository;

    @Value("${app.admin.apikey}")//inject a value from application.properties
    private String adminApiKey;

    public AuthorizationInterceptor(ApiKeyRepository apiKeyRepository)
    {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        String apiKey = request.getHeader(GeneralConstants.API_KEY);

        if(StringUtils.isNotBlank(apiKey))
        {
            if(request.getRequestURI().startsWith("/api/v1/users"))
            {
                if(adminApiKey.equals(apiKey)) return true;
            }
            else
            {
                Optional<ApiKey> apiKeyOptional = apiKeyRepository.findByIdApiKey(apiKey);
                if (apiKeyOptional.isPresent())
                {
                    request.setAttribute(GeneralConstants.USER, apiKeyOptional.get().getUser());
                    return true;
                }
            }
        }

        throw new ResponseException(APIResponse.INVALID_API_KEY);
    }
}
