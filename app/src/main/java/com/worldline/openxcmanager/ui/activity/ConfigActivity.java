package com.worldline.openxcmanager.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.worldline.openxcmanager.R;
import com.worldline.openxcmanager.ui.presenter.ApiClientPresenter;
import com.worldline.openxcmanagers.sdk.OpenXCResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by a557114 on 01/09/2015.
 */
public class ConfigActivity extends AppCompatActivity {

    private TextInputLayout ipInputLayout;
    private EditText editIp;

    private TextInputLayout portInputLayout;
    private EditText editPort;

    private MaterialDialog materialDialog;
    private View contentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_configuration);

        contentView = findViewById(R.id.contentView);

        ipInputLayout = (TextInputLayout) findViewById(R.id.edit_ip_input_layout);
        portInputLayout = (TextInputLayout) findViewById(R.id.edit_port_input_layout);

        editIp = (EditText) findViewById(R.id.edit_ip);
        editPort = (EditText) findViewById(R.id.edit_port);
        View buttonConfig = findViewById(R.id.button_config_server);
        buttonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = editIp.getText().toString();
                String port = editPort.getText().toString();

                checkIpAndPort(ip, port);

                if (!TextUtils.isEmpty(ip) && validIP(ip) && !TextUtils.isEmpty(port) && validPORT(port)) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString("APP_ID", ip);
                    edit.putInt("APP_PORT", Integer.valueOf(port));
                    edit.apply();
                    callConfig(ip, Integer.valueOf(port));
                }
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String ip = preferences.getString("APP_ID", null);
        int port = preferences.getInt("APP_PORT", -1);

        if (port > -1) {
            editIp.setText(ip);
            editPort.setText(String.valueOf(port));
            checkIpAndPort(ip, String.valueOf(port));
        }
    }

    private void checkIpAndPort(String ip, String port) {
        if (TextUtils.isEmpty(ip)) {
            ipInputLayout.setErrorEnabled(true);
            ipInputLayout.setError("IP cannot be empty");
        } else if (!validIP(ip)) {
            ipInputLayout.setErrorEnabled(true);
            ipInputLayout.setError("IP is not valid");
        }

        if (TextUtils.isEmpty(port)) {
            portInputLayout.setErrorEnabled(true);
            portInputLayout.setError("PORT cannot be empty");
        } else if (!validPORT(port)) {
            portInputLayout.setErrorEnabled(true);
            portInputLayout.setError("PORT is not valid");
        }
    }


    public static boolean validIP(String ip) {
        try {
            if (ip == null || ip.isEmpty()) {
                return false;
            }

            String[] parts = ip.split("\\.");
            if (parts.length != 4) {
                return false;
            }

            for (String s : parts) {
                int i = Integer.parseInt(s);
                if ((i < 0) || (i > 255)) {
                    return false;
                }
            }
            return !ip.endsWith(".");
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static boolean validPORT(String port) {
        try {
            if (port == null || port.isEmpty()) {
                return false;
            }

            if (TextUtils.isDigitsOnly(port)) {
                int portNum = Integer.valueOf(port);
                return !((portNum < 0) || (portNum > 65535));
            } else {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private void callConfig(String ip, int port) {

        materialDialog = new MaterialDialog.Builder(this)
                .content(R.string.please_wait)
                .progress(true, 0)
                .show();


        new ApiClientPresenter(this).checkConnection(new Callback<OpenXCResponse>() {
            @Override
            public void success(OpenXCResponse openXCResponse, Response response) {
                if (materialDialog != null && materialDialog.isShowing()) {
                    materialDialog.dismiss();
                    materialDialog = null;
                }

                Intent intent = new Intent(ConfigActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                if (materialDialog != null && materialDialog.isShowing()) {
                    materialDialog.dismiss();
                    materialDialog = null;
                }

                String extra = "";

                if (error != null && error.getLocalizedMessage() != null) {
                    extra = error.getLocalizedMessage();
                }

                Snackbar.make(contentView, "Cannot connect: " + extra, Snackbar.LENGTH_SHORT).show();
            }
        }, ip, port);
    }
}
