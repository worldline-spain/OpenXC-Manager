package com.worldline.openxcmanager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import com.worldline.openxcmanager.ui.cards.CardConfiguration;
import com.worldline.openxcmanager.ui.cards.CardVehicleMilDtc;
import com.worldline.openxcmanager.ui.presenter.ApiClientPresenter;
import com.worldline.openxcmanagers.sdk.OpenXCResponse;

import io.fabric.sdk.android.Fabric;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements ApiClientPresenter.ApiClientPresenterCallback, CardConfiguration.Listener, CardVehicleMilDtc.MilDtcCallback {

    private ApiClientPresenter presenter;
    private CardConfiguration cardConfig;
    private CardsAdapter adapter;
    private MaterialDialog materialDialog;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
        setContentView(R.layout.activity_main);

        findViews();

        presenter = new ApiClientPresenter(this);
    }

    private void findViews() {
        findConfig();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(getResources().getInteger(R.integer.grid_size), StaggeredGridLayoutManager.VERTICAL));
        adapter = new CardsAdapter(LayoutInflater.from(this), this);
        recyclerView.setAdapter(adapter);

    }

    private void findConfig() {
        cardConfig = (CardConfiguration) findViewById(R.id.card_config);
        cardConfig.setListener(this);
    }

    @Override
    public void showOpenXCData(boolean show) {
        if (cardConfig != null) {
            if (show) {
                cardConfig.setVisibility(View.GONE);
            } else {
                cardConfig.setVisibility(View.VISIBLE);
                adapter.clear();
            }
        }
    }

    @Override
    public void setData(OpenXCResponse openXCResponse) {
        adapter.setOpenXcData(openXCResponse);
    }

    @Override
    public void callConfig(String ip, int port) {

        materialDialog = new MaterialDialog.Builder(this)
                .content(R.string.please_wait)
                .progress(true, 0)
                .show();

        presenter.checkConnection(new Callback<OpenXCResponse>() {
            @Override
            public void success(OpenXCResponse openXCResponse, Response response) {
                if (materialDialog != null && materialDialog.isShowing()) {
                    materialDialog.dismiss();
                    materialDialog = null;
                }
                setData(openXCResponse);
                showOpenXCData(true);
            }

            @Override
            public void failure(RetrofitError error) {
                if (materialDialog != null && materialDialog.isShowing()) {
                    materialDialog.dismiss();
                    materialDialog = null;
                }
                Snackbar.make(recyclerView, "Cannot connect", Snackbar.LENGTH_SHORT).show();
            }
        }, ip, port);
    }

    @Override
    public void requestDtcCodes() {
        Intent intent = new Intent(this, DtcCodesActivity.class);
        startActivity(intent);
    }
}
