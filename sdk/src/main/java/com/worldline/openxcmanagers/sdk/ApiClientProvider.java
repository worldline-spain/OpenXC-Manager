package com.worldline.openxcmanagers.sdk;

import android.text.TextUtils;

import retrofit.Endpoint;

/**
 * Created by a557114 on 13/08/2015.
 */
public abstract class ApiClientProvider implements Endpoint {

    protected abstract String getEndpoint();

    public abstract int getPort();

    @Override
    public String getName() {
        return "OpenXC";
    }

    @Override
    public String getUrl() {
        if (TextUtils.isEmpty(getEndpoint())) {
            throw new IllegalArgumentException("Endpoint can not be empty");
        }

        String endpoint = getEndpoint();

        if (endpoint != null && (endpoint.endsWith(":") || endpoint.endsWith("/"))) {
            endpoint = endpoint.substring(0, endpoint.length() - 1);
        }

        return endpoint + ":" + getPort();
    }
}
