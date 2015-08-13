package com.worldline.openxcmanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements ApiClientPresenter.ApiClientPresenterCallback {

    private View cardVehicleControls;

    private EditText editIp;
    private EditText editPort;

    private SeekBar seekBarSteeringWheelAngle;

    private SeekBar seekBarAcceleratorPercentPercentage;
    private SeekBar seekBarBreakPercentPercentage;
    private ApiClientPresenter presenter;
    private View cardConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        presenter = new ApiClientPresenter();
    }

    private void findViews() {
        findCards();
        findToolbars();

        findConfig();

        seekBarSteeringWheelAngle = (SeekBar) findViewById(R.id.steering_wheel_angle);
        seekBarAcceleratorPercentPercentage = (SeekBar) findViewById(R.id.accelerator_percent_percentage);
        seekBarBreakPercentPercentage = (SeekBar) findViewById(R.id.break_percent_percentage);

    }

    private void findConfig() {
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

                    presenter.init(MainActivity.this, ip, Integer.valueOf(port));
                } else {
                    editPort.setError(getString(R.string.error_port_numeric));
                }
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String ip = preferences.getString("APP_ID", null);
        int port = preferences.getInt("APP_PORT", -1);

        if (!TextUtils.isEmpty(ip) && port > -1) {
            editIp.setText(ip);
            editPort.setText(String.valueOf(port));
        }
    }

    private void findCards() {
        cardVehicleControls = findViewById(R.id.card_vehicle_controls);
        cardConfig = findViewById(R.id.card_config);
    }

    private void findToolbars() {
        Toolbar toolbar_configuration = (Toolbar) findViewById(R.id.toolbar_configuration);
        toolbar_configuration.setTitle(R.string.configuration_title);
        Toolbar toolbar_vehicle_controls = (Toolbar) findViewById(R.id.toolbar_vehicle_controls);
        toolbar_vehicle_controls.setTitle(R.string.vehicle_controls_title);
    }

    @Override
    public void showVehicleControlCard(boolean show) {
        if (cardConfig != null) {
            cardConfig.setVisibility(View.GONE);
        }
        if (cardVehicleControls != null) {
            cardVehicleControls.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void steeringWheelAngle(int wheelAngle) {
        if (seekBarSteeringWheelAngle != null) {
            seekBarSteeringWheelAngle.setProgress(wheelAngle);
        }
    }

    @Override
    public void acceleratorPercentPercentage(int acceleratorPedalPosition) {
        if (seekBarAcceleratorPercentPercentage != null) {
            seekBarAcceleratorPercentPercentage.setProgress(acceleratorPedalPosition);
        }
    }

    @Override
    public void breakPercentPercentage(int breakPedalPosition) {
        if (seekBarBreakPercentPercentage != null) {
            seekBarBreakPercentPercentage.setProgress(breakPedalPosition);
        }
    }
}
