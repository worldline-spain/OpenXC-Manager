package com.worldline.openxcmanager.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.worldline.openxcmanager.R;
import com.worldline.openxcmanager.ui.adapter.DtcCodesAdapter;
import com.worldline.openxcmanager.ui.presenter.ApiClientPresenter;
import com.worldline.openxcmanagers.sdk.ApiClient;
import com.worldline.openxcmanagers.sdk.DtcVO;
import com.worldline.openxcmanagers.sdk.OpenXCResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DtcCodesActivity extends AppCompatActivity implements DtcCodesAdapter.DtcCallback, ApiClientPresenter.ApiClientPresenterCallback {

    private DtcCodesAdapter adapter;
    private RecyclerView recyclerView;
    private ApiClientPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dtc_codes);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        presenter = new ApiClientPresenter(this);
        presenter.init(this);

        findViews();
    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        createAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void createAdapter() {
        adapter = new DtcCodesAdapter(LayoutInflater.from(this), presenter.getAllDtcCodes());
        adapter.setDtcCallback(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sendDTC(DtcVO dtcVO) {
        presenter.modifyDtc(dtcVO, true);
        presenter.sendCustomMessage(dtcVO.getDtcCode(), "true", "DTC_ERROR");
    }

    @Override
    public void cancelDTC(DtcVO dtcVO) {
        presenter.modifyDtc(dtcVO, false);
        presenter.sendCustomMessage(dtcVO.getDtcCode(), "false", "DTC_ERROR");
    }

    @Override
    public void setData(OpenXCResponse openXCResponse) {
        createAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void failure() {
        Snackbar.make(recyclerView, "Failed", Snackbar.LENGTH_SHORT).show();
    }
}
