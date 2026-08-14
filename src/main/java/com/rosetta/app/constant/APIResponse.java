package com.rosetta.app.constant;

import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public enum APIResponse
{
    SUCCESS(HttpServletResponse.SC_OK, 1000, "%s"),

    GENERIC_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 1001, "Error"),
    INVALID_API_KEY(HttpServletResponse.SC_UNAUTHORIZED, 1002, "Invalid API Key"),
    INVALID_USER_ID(HttpServletResponse.SC_NOT_FOUND, 1003, "Invalid User ID"),
    INVALID_TRANSLATION_ID(HttpServletResponse.SC_NOT_FOUND, 1004, "Invalid Translation ID"),
    TRANSLATION_NOT_YET_PROCESSED(HttpServletResponse.SC_ACCEPTED, 1005, "Translation not yet processed. Try again later."),
    FETCH_TRANSLATION_PERMISSION_DENIED(HttpServletResponse.SC_FORBIDDEN, 1006, "Permission denied to fetch this translation."),
    RECORDS_NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, 1007, "Records not found."),
    MAX_LIMIT_ERROR(HttpServletResponse.SC_NOT_FOUND, 1008, "Max limit is 50.");

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

    public static JSONObject getSuccessJsonObj()
    {
        return new JSONObject()
                .put(ResponseConstants.CODE, SUCCESS.code);
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