package com.worldline.openxcmanagers.sdk.service;

import com.worldline.openxcmanagers.sdk.OpenXCResponse;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by a557114 on 13/08/2015.
 */
public interface OpenXCService {

    @GET("/_get_data")
    void getData(Callback<OpenXCResponse> callback);

    @POST("/_set_data")
    @FormUrlEncoded
    void postData(@Field("name") String name, @Field("value") String value, Callback<Response> callback);

    @POST("/custom-message")
    @FormUrlEncoded
    void customMessage(@Field("custom_message_name") String custom_message_name,
                       @Field("custom_message_value") String custom_message_value,
                       @Field("custom_message_event") String custom_message_event,
                       Callback<Response> callback);
}
