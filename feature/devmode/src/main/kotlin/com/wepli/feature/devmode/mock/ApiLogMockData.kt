package com.wepli.feature.devmode.mock

import com.wepli.domain.devmode.apilog.model.ApiLog
import com.wepli.domain.devmode.apilog.model.ApiMethod

val mockApiLogs = listOf(
    ApiLog(
        id = 0,
        method = ApiMethod.GET,
        baseUrlType = "Production",
        baseUrl = "https://api.example.com",
        url = "/users",
        requestHeaders = mapOf(),
        requestBody = "",
        responseCode = 200,
        responseBody = "[{\"id\":1,\"name\":\"Alice\"},{\"id\":2,\"name\":\"Bob\"}]",
        startTime = System.currentTimeMillis(),
        durationMs = 120
    ),
    ApiLog(
        id = 1,
        method = ApiMethod.POST,
        baseUrlType = "Production",
        baseUrl = "https://api.example.com",
        url = "/login",
        requestHeaders = mapOf(),
        requestBody = "{\"username\":\"john\",\"password\":\"secret\"}",
        responseCode = 401,
        responseBody = "{\"error\":\"Invalid credentials\"}",
        startTime = System.currentTimeMillis(),
        durationMs = 98
    ),
    ApiLog(
        id = 2,
        method = ApiMethod.PUT,
        baseUrlType = "Production",
        baseUrl = "https://api.example.com",
        url = "/users/1",
        requestHeaders = mapOf(),
        requestBody = "{\"name\":\"Alice Updated\"}",
        responseCode = 200,
        responseBody = "{\"id\":1,\"name\":\"Alice Updated\"}",
        startTime = System.currentTimeMillis(),
        durationMs = 150
    ),
    ApiLog(
        id = 3,
        method = ApiMethod.DELETE,
        baseUrlType = "Production",
        baseUrl = "https://api.example.com",
        url = "/users/2",
        requestHeaders = mapOf(),
        requestBody = "",
        responseCode = 204,
        responseBody = "",
        startTime = System.currentTimeMillis(),
        durationMs = 85
    )
)