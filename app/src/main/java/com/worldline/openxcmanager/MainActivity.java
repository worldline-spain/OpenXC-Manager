package com.worldline.openxcmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.worldline.openxcmanager.ui.adapter.CardsAdapter;
import com.worldline.openxcmanager.ui.cards.CardConfiguration;
import com.worldline.openxcmanagers.sdk.OpenXCResponse;

public class MainActivity extends AppCompatActivity implements ApiClientPresenter.ApiClientPresenterCallback, CardConfiguration.Listener {

    private ApiClientPresenter presenter;
    private CardConfiguration cardConfig;
    private CardsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        presenter = new ApiClientPresenter();
    }

    private void findViews() {
        findConfig();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(getResources().getInteger(R.integer.grid_size), StaggeredGridLayoutManager.VERTICAL));
        adapter = new CardsAdapter(LayoutInflater.from(this));
        recyclerView.setAdapter(adapter);

    }

    private void findConfig() {
        cardConfig = (CardConfiguration) findViewById(R.id.card_config);
        cardConfig.setListener(this);
    }

    @Override
    public void showVehicleControlCard(boolean show) {
        if (cardConfig != null) {
            cardConfig.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void setData(OpenXCResponse openXCResponse) {
        adapter.setOpenXcData(openXCResponse);
    }

    @Override
    public void callConfig(String ip, int port) {
        presenter.init(this, ip, port);
    }
}
