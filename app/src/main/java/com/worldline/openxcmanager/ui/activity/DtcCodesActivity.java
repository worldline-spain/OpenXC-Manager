package com.worldline.openxcmanager.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.worldline.openxcmanager.R;
import com.worldline.openxcmanager.ui.adapter.CardsAdapter;
import com.worldline.openxcmanager.ui.adapter.DtcCodesAdapter;
import com.worldline.openxcmanager.ui.presenter.ApiClientPresenter;

public class DtcCodesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dtc_codes);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        findViews();
    }

    private void findViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(getResources().getInteger(R.integer.grid_size), StaggeredGridLayoutManager.VERTICAL));
        DtcCodesAdapter adapter = new DtcCodesAdapter(LayoutInflater.from(this), new ApiClientPresenter(this).getAllDtcCodes());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
