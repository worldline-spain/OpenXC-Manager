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
    private CompoundButton buttonAccessory;
    private CompoundButton buttonRun;
    private CompoundButton buttonStart;

    private CompoundButton gearPark;
    private CompoundButton gearDrive;
    private CompoundButton gearNeutral;
    private CompoundButton gearReverse;

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

        Toolbar toolbar_vehicle_controls = (Toolbar) findViewById(R.id.toolbar);
        toolbar_vehicle_controls.setTitle(R.string.vehicle_status_title);

        buttonOff = (CompoundButton) findViewById(R.id.ignition_status_off);
        buttonAccessory = (CompoundButton) findViewById(R.id.ignition_status_accessory);
        buttonRun = (CompoundButton) findViewById(R.id.ignition_status_run);
        buttonStart = (CompoundButton) findViewById(R.id.ignition_status_start);

        gearPark = (CompoundButton) findViewById(R.id.gear_position_park);
        gearDrive = (CompoundButton) findViewById(R.id.gear_position_drive);
        gearNeutral = (CompoundButton) findViewById(R.id.gear_position_neutral);
        gearReverse = (CompoundButton) findViewById(R.id.gear_position_reverse);
    }

    @Override
    public void setData(OpenXCResponse openXCResponse) {
        if (openXCResponse != null) {
            manageIngnitionButtons(openXCResponse.ignitionStatus);
            manageGearButtons(openXCResponse.gearLeverPosition);
        }
    }

    private void manageIngnitionButtons(String ignitionStatus) {
        disableIgnitionButtons();
        switch (ignitionStatus) {
            case "off":
                if (buttonOff != null) {
                    buttonOff.setChecked(true);
                }
                break;
            case "accessory":
                if (buttonAccessory != null) {
                    buttonAccessory.setChecked(true);
                }
                break;
            case "run":
                if (buttonRun != null) {
                    buttonRun.setChecked(true);
                }
                break;
            case "start":
                if (buttonStart != null) {
                    buttonStart.setChecked(true);
                }
                break;
        }
    }

    private void disableIgnitionButtons() {
        if (buttonOff != null) {
            buttonOff.setChecked(false);
        }
        if (buttonAccessory != null) {
            buttonAccessory.setChecked(false);
        }
        if (buttonRun != null) {
            buttonRun.setChecked(false);
        }
        if (buttonStart != null) {
            buttonStart.setChecked(false);
        }
    }

    private void manageGearButtons(String gearLeverPosition) {
        disableGearButtons();
        switch (gearLeverPosition) {
            case "park":
                if (gearPark != null) {
                    gearPark.setChecked(true);
                }
                break;
            case "drive":
                if (gearDrive != null) {
                    gearDrive.setChecked(true);
                }
                break;
            case "neutral":
                if (gearNeutral != null) {
                    gearNeutral.setChecked(true);
                }
                break;
            case "reverse":
                if (gearReverse != null) {
                    gearReverse.setChecked(true);
                }
                break;
        }
    }

    private void disableGearButtons() {
        if (gearPark != null) {
            gearPark.setChecked(false);
        }
        if (gearDrive != null) {
            gearDrive.setChecked(false);
        }
        if (gearNeutral != null) {
            gearNeutral.setChecked(false);
        }
        if (gearReverse != null) {
            gearReverse.setChecked(false);
        }
    }

}
