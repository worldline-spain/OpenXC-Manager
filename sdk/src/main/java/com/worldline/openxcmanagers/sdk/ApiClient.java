package com.worldline.openxcmanagers.sdk;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.worldline.openxcmanagers.sdk.service.OpenXCService;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class ApiClient {

    private static ApiClient INSTANCE;
    private final ApiClientProvider clientProvider;
    private RestAdapter.LogLevel logLevel;
    private RestAdapter.Log log;

    public static ApiClient init(ApiClientProvider clientProvider) {
        if (clientProvider == null) {
            throw new IllegalArgumentException("ApiClientProvider can not be null");
        }

        INSTANCE = new ApiClient(clientProvider);

        return INSTANCE;
    }

    public static ApiClient getInstance() {
        return INSTANCE;
    }

    private ApiClient(ApiClientProvider apiClientProvider) {
        clientProvider = apiClientProvider;
    }

    public void getData(Callback<OpenXCResponse> callback) {
        RestAdapter restAdapter = createRestAdapter();
        restAdapter.create(OpenXCService.class).getData(callback);
    }

    public void postData(String key, String value, Callback<OpenXCResponse> callback) {
        RestAdapter restAdapter = createRestAdapter();
        restAdapter.create(OpenXCService.class).postData(key, value, new WaitCallback(callback));
    }

    public void customMessage(String name, String value, String event, Callback<OpenXCResponse> callback) {
        RestAdapter restAdapter = createRestAdapter();
        restAdapter.create(OpenXCService.class).customMessage(name, value, event, new WaitCallback(callback));
    }

    private RestAdapter createRestAdapter() {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setClient(new OkClient(new OkHttpClient()));
        builder.setEndpoint(clientProvider);

        builder.setLogLevel(RestAdapter.LogLevel.FULL);
        builder.setLog(new RestAdapter.Log() {
            @Override
            public void log(String message) {
                Log.w("OpenXCManager", message);
            }
        });

        return builder.build();
    }

    private class WaitCallback implements Callback<Response> {
        private final Callback<OpenXCResponse> callback;

        public WaitCallback(Callback<OpenXCResponse> callback) {
            this.callback = callback;
        }

        @Override
        public void success(Response response, Response response2) {
            if (callback != null) {
                getData(callback);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            if (callback != null) {
                callback.failure(error);
            }
        }
    }
}
