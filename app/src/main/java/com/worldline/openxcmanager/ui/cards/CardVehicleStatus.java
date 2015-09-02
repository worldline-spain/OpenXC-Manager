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

    private CompoundButton buttonOff;
    private CompoundButton buttonStart;

    private CompoundButton.OnCheckedChangeListener compoundIgnitionStatusListener;

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

        buttonOff = (CompoundButton) findViewById(R.id.ignition_status_off);
        buttonOff.setTag("off");
        buttonStart = (CompoundButton) findViewById(R.id.ignition_status_start);
        buttonStart.setTag("start");

        compoundIgnitionStatusListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                buttonOff.setOnCheckedChangeListener(null);
                buttonStart.setOnCheckedChangeListener(null);

                buttonOff.setChecked(false);
                buttonStart.setChecked(false);

                buttonView.setChecked(isChecked);

                if (requestListener != null) {
                    requestListener.postData("ignition_status", String.valueOf(buttonView.getTag()));
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
        disableIgnitionButtons();

        if (buttonOff != null) {
            buttonOff.setOnCheckedChangeListener(null);
        }
        if (buttonStart != null) {
            buttonStart.setOnCheckedChangeListener(null);
        }
        switch (ignitionStatus) {
            case "off":
                if (buttonOff != null) {
                    buttonOff.setChecked(true);
                }
                break;
            case "start":
                if (buttonStart != null) {
                    buttonStart.setChecked(true);
                }
                break;
        }

        if (buttonOff != null) {
            buttonOff.setOnCheckedChangeListener(compoundIgnitionStatusListener);
        }

        if (buttonStart != null) {
            buttonStart.setOnCheckedChangeListener(compoundIgnitionStatusListener);
        }
    }

    private void disableIgnitionButtons() {
        if (buttonOff != null) {
            buttonOff.setChecked(false);
        }

        if (buttonStart != null) {
            buttonStart.setChecked(false);
        }
    }

}
