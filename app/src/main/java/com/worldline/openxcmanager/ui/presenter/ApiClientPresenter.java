package com.worldline.openxcmanager.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.worldline.openxcmanagers.sdk.ApiClient;
import com.worldline.openxcmanagers.sdk.ApiClientProvider;
import com.worldline.openxcmanagers.sdk.DtcLoad;
import com.worldline.openxcmanagers.sdk.DtcVO;
import com.worldline.openxcmanagers.sdk.OpenXCResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by a557114 on 13/08/2015.
 */
public class ApiClientPresenter {

    private final DtcLoad dtcLoad;
    private ApiClientPresenterCallback apiClientPresenterCallback;

    private Callback<OpenXCResponse> callback = new Callback<OpenXCResponse>() {

        @Override
        public void success(OpenXCResponse openXCResponse, Response response) {
            if (apiClientPresenterCallback != null) {
                apiClientPresenterCallback.setData(openXCResponse);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            if (apiClientPresenterCallback != null) {
                apiClientPresenterCallback.failure();
            }
        }
    };

    public ApiClientPresenter(Context context) {
        dtcLoad = new DtcLoad(context);
        dtcLoad.loadDTCFromCSV();
    }

    public void checkConnection(Callback<OpenXCResponse> callback, String ip, int port) {

        if (TextUtils.isEmpty(ip)) {
            throw new IllegalArgumentException("IP can not be null");
        }

        if (!ip.startsWith("http://") || !ip.startsWith("https://")) {
            ip = "http://" + ip;
        }

        ApiClientProvider apiClientProvider = new APiConnection(ip, port);

        ApiClient.init(apiClientProvider);

        ApiClient.getInstance().getData(callback);
    }

    public void init(ApiClientPresenterCallback apiClientPresenterCallback) {
        this.apiClientPresenterCallback = apiClientPresenterCallback;
    }

    public void getData() {
        ApiClient.getInstance().getData(callback);
    }

    public void modifyDtc(DtcVO dtcVO, boolean isActive) {
        dtcLoad.modifyDtc(dtcVO, (isActive ? 1 : 0));
    }

    public void sendCustomMessage(String key, String value, String event) {
        ApiClient.getInstance().customMessage(key, value, event, callback);
    }

    public void sendPostData(String key, String value) {
        ApiClient.getInstance().postData(key, value, callback);
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

    public List<DtcVO> getAllDtcCodes() {
        return dtcLoad.getDtcCodes();
    }

    public interface ApiClientPresenterCallback {
        void setData(OpenXCResponse openXCResponse);

        void failure();
    }
}
