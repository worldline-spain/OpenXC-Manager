package com.worldline.openxcmanager;

import android.os.Handler;

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
            manageSeekBarSteeringWheelAngle(openXCResponse.steeringWheelAngle);
            manageAcceleratorPercentPercentage(openXCResponse.acceleratorPedalPosition);
        }

        private void manageSeekBarSteeringWheelAngle(int steeringWheelAngle) {
            int displayAngle = steeringWheelAngle + 600;

            if (apiClientPresenterCallback != null) {
                apiClientPresenterCallback.steeringWheelAngle(displayAngle);
            }
        }

        private void manageAcceleratorPercentPercentage(int acceleratorPedalPosition) {
            if (apiClientPresenterCallback != null) {
                apiClientPresenterCallback.acceleratorPercentPercentage(acceleratorPedalPosition);
            }
        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    public void init(ApiClientPresenterCallback apiClientPresenterCallback) {
        this.apiClientPresenterCallback = apiClientPresenterCallback;
        ApiClientProvider apiClientProvider = new ApiClientProvider() {

            @Override
            protected String getEndpoint() {
                return "http://192.168.2.115";
            }

            @Override
            public int getPort() {
                return 50000;
            }
        };

        ApiClient.init(apiClientProvider);

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ApiClient.getInstance().getData(callback);
                handler.postDelayed(this, 200);
            }
        };
        handler.post(runnable);
    }

    public interface ApiClientPresenterCallback {
        void steeringWheelAngle(int wheelAngle);
        void acceleratorPercentPercentage(int acceleratorPedalPosition);
    }
}
