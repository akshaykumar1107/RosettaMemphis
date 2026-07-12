package com.rosetta.app.exception;

import com.rosetta.app.constant.APIResponse;

public class ResponseException extends Exception
{
    private final APIResponse apiResponse;

    public APIResponse getApiResponse()
    {
        return apiResponse;
    }

    public ResponseException(APIResponse apiResponse)
    {
        this.apiResponse = apiResponse;
    }
}
