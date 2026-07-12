package com.rosetta.app.constant;

import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public enum APIResponse
{
    SUCCESS(HttpServletResponse.SC_OK, 1000, "%s"),

    GENERIC_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 1001, "Error");

    private final int httpStatus;
    private final int code;
    private final String message;

    public int getHttpStatus()
    {
        return httpStatus;
    }

    public int getCode()
    {
        return code;
    }

    public String toResponseString(String customMessage)
    {
        return new JSONObject()
                .put(ResponseConstants.CODE, code)
                .put(ResponseConstants.MESSAGE, customMessage != null ? String.format(message, customMessage) : message)
                .toString();
    }

    public String toResponseString()
    {
        return toResponseString(null);
    }

    APIResponse(int httpStatus, int code, String message)
    {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}