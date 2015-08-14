package com.worldline.openxcmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.worldline.openxcmanager.ui.cards.BaseCardView;

/**
 * Created by a557114 on 14/08/2015.
 */
public class CardConfiguration extends BaseCardView {

    private EditText editIp;
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

        editIp = (EditText) findViewById(R.id.edit_ip);
        editPort = (EditText) findViewById(R.id.edit_port);
        View buttonConfig = findViewById(R.id.button_config_server);
        buttonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = editIp.getText().toString();
                String port = editPort.getText().toString();

                if (TextUtils.isDigitsOnly(port)) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString("APP_ID", ip);
                    edit.putInt("APP_PORT", Integer.valueOf(port));
                    edit.apply();
                    if (listener != null) {
                        listener.callConfig(ip, Integer.valueOf(port));
                    }

                } else {
                    editPort.setError(getResources().getString(R.string.error_port_numeric));
                }
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        String ip = preferences.getString("APP_ID", null);
        int port = preferences.getInt("APP_PORT", -1);

        if (!TextUtils.isEmpty(ip) && port > -1) {
            editIp.setText(ip);
            editPort.setText(String.valueOf(port));
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
