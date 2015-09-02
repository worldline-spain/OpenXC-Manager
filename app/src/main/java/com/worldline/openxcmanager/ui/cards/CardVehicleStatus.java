package com.worldline.openxcmanager.ui.cards;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import com.worldline.openxcmanager.R;
import com.worldline.openxcmanager.ui.cards.base.CardOpenXC;
import com.worldline.openxcmanagers.sdk.OpenXCResponse;

/**
 * Created by a557114 on 14/08/2015.
 */
public class CardVehicleStatus extends CardOpenXC {

    private CompoundButton.OnCheckedChangeListener compoundIgnitionStatusListener;
    private CompoundButton buttonIgnitionStatus;

    public CardVehicleStatus(Context context) {
        super(context);
        init();
    }

    public CardVehicleStatus(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CardVehicleStatus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.card_vehicle_status, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.vehicle_status_title);

        buttonIgnitionStatus = (CompoundButton) findViewById(R.id.ignition_status);
        compoundIgnitionStatusListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (requestListener != null) {
                    requestListener.postData("ignition_status", String.valueOf(isChecked ? "start" : "off"));
                }
            }
        };
    }

    @Override
    public void setData(OpenXCResponse openXCResponse) {
        if (openXCResponse != null) {
            manageIgnitionButtons(openXCResponse.ignitionStatus);
        }
    }

    private void manageIgnitionButtons(String ignitionStatus) {
        buttonIgnitionStatus.setOnCheckedChangeListener(null);
        buttonIgnitionStatus.setChecked((ignitionStatus != null && ignitionStatus.equalsIgnoreCase("start")));
        buttonIgnitionStatus.setOnCheckedChangeListener(compoundIgnitionStatusListener);
    }

}
