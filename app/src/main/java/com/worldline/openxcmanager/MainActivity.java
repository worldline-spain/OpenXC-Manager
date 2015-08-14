package com.worldline.openxcmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.worldline.openxcmanagers.sdk.OpenXCResponse;

public class MainActivity extends AppCompatActivity implements ApiClientPresenter.ApiClientPresenterCallback, CardConfiguration.Listener {

    private ApiClientPresenter presenter;
    private CardConfiguration cardConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        presenter = new ApiClientPresenter();
    }

    private void findViews() {
        findConfig();
    }

    private void findConfig() {
        cardConfig = (CardConfiguration) findViewById(R.id.card_config);
        cardConfig.setListener(this);
    }

    @Override
    public void showVehicleControlCard(boolean show) {
        if (cardConfig != null) {
            cardConfig.setVisibility(View.GONE);
        }
    }

    @Override
    public void setData(OpenXCResponse openXCResponse) {

    }

    @Override
    public void callConfig(String ip, int port) {
        presenter.init(this, ip, port);
    }
}
