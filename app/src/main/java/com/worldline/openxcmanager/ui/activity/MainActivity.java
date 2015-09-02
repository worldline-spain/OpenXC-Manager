package com.worldline.openxcmanager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.crashlytics.android.Crashlytics;
import com.worldline.openxcmanager.BuildConfig;
import com.worldline.openxcmanager.R;
import com.worldline.openxcmanager.ui.adapter.CardsAdapter;
import com.worldline.openxcmanager.ui.cards.CardVehicleMilDtc;
import com.worldline.openxcmanager.ui.presenter.ApiClientPresenter;
import com.worldline.openxcmanagers.sdk.OpenXCResponse;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements ApiClientPresenter.ApiClientPresenterCallback, CardVehicleMilDtc.MilDtcCallback {

    private CardsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
        setContentView(R.layout.activity_main);

        findViews();

        ApiClientPresenter presenter = new ApiClientPresenter(this);
        presenter.init(this);
    }

    private void findViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(getResources().getInteger(R.integer.grid_size), StaggeredGridLayoutManager.VERTICAL));
        adapter = new CardsAdapter(LayoutInflater.from(this), this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void showOpenXCData(boolean show) {
        if (!show) {
            adapter.clear();
        }
    }

    @Override
    public void setData(OpenXCResponse openXCResponse) {
        adapter.setOpenXcData(openXCResponse);
    }

    @Override
    public void requestDtcCodes() {
        Intent intent = new Intent(this, DtcCodesActivity.class);
        startActivity(intent);
    }
}
