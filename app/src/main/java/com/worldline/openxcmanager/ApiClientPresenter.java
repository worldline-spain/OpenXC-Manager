package com.worldline.openxcmanager;

import android.os.Handler;
import android.text.TextUtils;

import com.worldline.openxcmanagers.sdk.ApiClient;
import com.worldline.openxcmanagers.sdk.ApiClientProvider;
import com.worldline.openxcmanagers.sdk.OpenXCResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by a557114 on 13/08/2015.
 */
public class ApiClientPresenter {

    private ApiClientPresenterCallback apiClientPresenterCallback;

    private Callback<OpenXCResponse> callback = new Callback<OpenXCResponse>() {
        @Override
        public void success(OpenXCResponse openXCResponse, Response response) {
            if (apiClientPresenterCallback != null) {
                apiClientPresenterCallback.showVehicleControlCard(true);
                apiClientPresenterCallback.setData(openXCResponse);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            if (apiClientPresenterCallback != null) {
                apiClientPresenterCallback.showVehicleControlCard(false);
            }
        }
    };

    public void init(ApiClientPresenterCallback apiClientPresenterCallback, String ip, int port) {
        this.apiClientPresenterCallback = apiClientPresenterCallback;

        if (TextUtils.isEmpty(ip)) {
            throw new IllegalArgumentException("IP can not be null");
        }

        if (!ip.startsWith("http://") || !ip.startsWith("https://")) {
            ip = "http://" + ip;
        }

        ApiClientProvider apiClientProvider = new APiConnection(ip, port);

        ApiClient.init(apiClientProvider);

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ApiClient.getInstance().getData(callback);
                handler.postDelayed(this, 500);
            }
        };
        handler.post(runnable);
    }

    private class APiConnection extends ApiClientProvider {

        private final String ip;
        private final int port;

        public APiConnection(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        protected String getEndpoint() {
            return ip;
        }

        @Override
        public int getPort() {
            return port;
        }
    }

    public interface ApiClientPresenterCallback {
        void showVehicleControlCard(boolean show);
        void setData(OpenXCResponse openXCResponse);
    }
}
