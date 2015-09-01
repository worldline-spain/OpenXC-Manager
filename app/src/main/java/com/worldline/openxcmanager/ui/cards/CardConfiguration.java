package com.worldline.openxcmanager.ui.cards;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.worldline.openxcmanager.R;
import com.worldline.openxcmanager.ui.cards.base.BaseCardView;

/**
 * Created by a557114 on 14/08/2015.
 */
public class CardConfiguration extends BaseCardView {

    private TextInputLayout ipInputLayout;
    private EditText editIp;

    private TextInputLayout portInputLayout;
    private EditText editPort;

    private Listener listener;

    public CardConfiguration(Context context) {
        super(context);
        init();
    }

    public CardConfiguration(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CardConfiguration(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.card_configuration, this);

        Toolbar toolbar_configuration = (Toolbar) findViewById(R.id.toolbar_configuration);
        toolbar_configuration.setTitle(R.string.configuration_title);

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
                    if (listener != null) {
                        listener.callConfig(ip, Integer.valueOf(port));
                    }

                }
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

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

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void callConfig(String ip, int port);
    }
}
