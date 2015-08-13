package com.worldline.openxcmanagers.sdk;

import com.worldline.openxcmanagers.sdk.service.OpenXCService;

import retrofit.Callback;
import retrofit.RestAdapter;

public class ApiClient {

    private static ApiClient INSTANCE;
    private final ApiClientProvider clientProvider;
    private RestAdapter.LogLevel logLevel;
    private RestAdapter.Log log;

    public static ApiClient init(ApiClientProvider clientProvider) {
        if (clientProvider == null) {
            throw new IllegalArgumentException("ApiClientProvider can not be null");
        }

        if (INSTANCE == null) {
            INSTANCE = new ApiClient(clientProvider);
        }
        return INSTANCE;
    }

    public static ApiClient getInstance() {
        if (INSTANCE == null) {
            throw new IllegalArgumentException("ApiClient should be initialized before call getInstance()");
        }
        return INSTANCE;
    }

    private ApiClient(ApiClientProvider apiClientProvider) {
        clientProvider = apiClientProvider;
    }

    public void getData(Callback<OpenXCResponse> callback) {
        RestAdapter restAdapter = createRestAdapter();
        restAdapter.create(OpenXCService.class).getData(callback);
    }

    private RestAdapter createRestAdapter() {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(clientProvider);

        if (logLevel != null && log != null) {
            builder.setLogLevel(logLevel);
            builder.setLog(log);
        }

        return builder.build();
    }

    public void setLog(RestAdapter.LogLevel logLevel, RestAdapter.Log log) {
        this.logLevel = logLevel;
        this.log = log;
    }

}
