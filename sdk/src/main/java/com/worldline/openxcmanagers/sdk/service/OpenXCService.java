package com.worldline.openxcmanagers.sdk.service;

import com.worldline.openxcmanagers.sdk.OpenXCResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;

/**
 * Created by a557114 on 13/08/2015.
 */
public interface OpenXCService {

    @GET("/_get_data")
    @Headers("Content-Type: text/html; charset=utf-8")
    void getData(Callback<OpenXCResponse> callback);

}
