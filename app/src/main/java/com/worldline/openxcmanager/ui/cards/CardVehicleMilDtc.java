package com.worldline.openxcmanager.ui.cards;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;

import com.worldline.openxcmanager.R;
import com.worldline.openxcmanager.ui.cards.base.CardOpenXC;
import com.worldline.openxcmanager.ui.widget.CenterSeekBar;
import com.worldline.openxcmanagers.sdk.ApiClient;
import com.worldline.openxcmanagers.sdk.OpenXCResponse;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

/**
 * Created by a557114 on 14/08/2015.
 */
public class CardVehicleMilDtc extends CardOpenXC {

    private Callback<Response> responseCallback;
    private boolean widgetsEnabled = true;
    private MilDtcCallback milDtcCallback;

    public CardVehicleMilDtc(Context context) {
        super(context);
        init();
    }

    public CardVehicleMilDtc(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CardVehicleMilDtc(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.card_vehicle_mil_dtc, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.vehicle_mil_dtc_title);

        View sendMIL = findViewById(R.id.sendMIL);
        View sendDTC = findViewById(R.id.sendDTC);
        View sendOK = findViewById(R.id.sendNoError);

        sendMIL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                widgetsEnabled = false;

                ApiClient.getInstance().setLog(RestAdapter.LogLevel.FULL, new AndroidLog("CardVehicleMilDtc"));
                ApiClient.getInstance().customMessage("DONGLE_LR_MIL", "true", "mil", responseCallback);
            }
        });

        sendOK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                widgetsEnabled = false;

                ApiClient.getInstance().customMessage("DONGLE_LR_MIL", "false", "mil", responseCallback);
            }
        });

        sendDTC.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (milDtcCallback != null) {
                    milDtcCallback.requestDtcCodes();
                }
            }
        });

        responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                widgetsEnabled = true;
            }

            @Override
            public void failure(RetrofitError error) {
                widgetsEnabled = true;
            }
        };
    }

    @Override
    public void setData(OpenXCResponse openXCResponse) {

    }

    public void setMilDtcCallback(MilDtcCallback milDtcCallback) {
        this.milDtcCallback = milDtcCallback;
    }

    public interface MilDtcCallback {
        void requestDtcCodes();
    }

}
