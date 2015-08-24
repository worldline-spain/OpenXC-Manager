package com.worldline.openxcmanager.ui.cards;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.worldline.openxcmanager.R;
import com.worldline.openxcmanager.ui.cards.base.CardOpenXC;
import com.worldline.openxcmanagers.sdk.ApiClient;
import com.worldline.openxcmanagers.sdk.OpenXCResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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

    private CompoundButton parkingBreakActive;
    private CompoundButton shiftTransmission;
    private TextView shiftTransmissionPosition;

    private TextView shiftTransmissionPositionDown;
    private TextView shiftTransmissionPositionUp;


    private boolean widgetsEnabled;
    private CompoundButton.OnCheckedChangeListener compoundIgnitionStatusListener;
    private CompoundButton.OnCheckedChangeListener checkListener;

    private OnClickListener clickDownShift;
    private OnClickListener clickUpShift;

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
        buttonAccessory = (CompoundButton) findViewById(R.id.ignition_status_accessory);
        buttonAccessory.setTag("accessory");
        buttonRun = (CompoundButton) findViewById(R.id.ignition_status_run);
        buttonRun.setTag("run");
        buttonStart = (CompoundButton) findViewById(R.id.ignition_status_start);
        buttonStart.setTag("start");

        gearPark = (CompoundButton) findViewById(R.id.gear_position_park);
        gearDrive = (CompoundButton) findViewById(R.id.gear_position_drive);
        gearNeutral = (CompoundButton) findViewById(R.id.gear_position_neutral);
        gearReverse = (CompoundButton) findViewById(R.id.gear_position_reverse);

        parkingBreakActive = (CompoundButton) findViewById(R.id.parking_break_active);
        parkingBreakActive.setTag("parking_brake_status");
        shiftTransmission = (CompoundButton) findViewById(R.id.shift_transmission);
        shiftTransmission.setTag("manual_trans_status");
        shiftTransmissionPosition = (TextView) findViewById(R.id.shift_transmission_position);
        shiftTransmissionPositionDown = (TextView) findViewById(R.id.shift_transmission_position_down);
        shiftTransmissionPositionUp = (TextView) findViewById(R.id.shift_transmission_position_up);

        final Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                widgetsEnabled = true;
            }

            @Override
            public void failure(RetrofitError error) {
                widgetsEnabled = true;
            }
        };

        compoundIgnitionStatusListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                widgetsEnabled = false;
                buttonOff.setOnCheckedChangeListener(null);
                buttonAccessory.setOnCheckedChangeListener(null);
                buttonRun.setOnCheckedChangeListener(null);
                buttonStart.setOnCheckedChangeListener(null);

                buttonOff.setChecked(false);
                buttonAccessory.setChecked(false);
                buttonRun.setChecked(false);
                buttonStart.setChecked(false);

                ApiClient.getInstance().postData("ignition_status", String.valueOf(buttonView.getTag()), responseCallback);
            }
        };

        buttonOff.setOnCheckedChangeListener(compoundIgnitionStatusListener);
        buttonAccessory.setOnCheckedChangeListener(compoundIgnitionStatusListener);
        buttonRun.setOnCheckedChangeListener(compoundIgnitionStatusListener);
        buttonStart.setOnCheckedChangeListener(compoundIgnitionStatusListener);

        checkListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                widgetsEnabled = false;

                parkingBreakActive.setOnCheckedChangeListener(null);
                shiftTransmission.setOnCheckedChangeListener(null);

                ApiClient.getInstance().postData(String.valueOf(buttonView.getTag()), String.valueOf(isChecked), responseCallback);
            }
        };
        parkingBreakActive.setOnCheckedChangeListener(checkListener);
        shiftTransmission.setOnCheckedChangeListener(checkListener);

        clickDownShift = new OnClickListener() {
            @Override
            public void onClick(View v) {
                widgetsEnabled = true;
                shiftTransmissionPositionDown.setOnClickListener(null);
                ApiClient.getInstance().postData("downshift", null, responseCallback);
            }
        };
        clickUpShift = new OnClickListener() {
            @Override
            public void onClick(View v) {
                widgetsEnabled = true;
                shiftTransmissionPositionUp.setOnClickListener(null);
                ApiClient.getInstance().postData("upshift", null, responseCallback);
            }
        };
    }

    @Override
    public void setData(OpenXCResponse openXCResponse) {
        if (openXCResponse != null && widgetsEnabled) {
            manageIngnitionButtons(openXCResponse.ignitionStatus);
            manageGearButtons(openXCResponse.gearLeverPosition);

            if (parkingBreakActive != null) {
                parkingBreakActive.setChecked(openXCResponse.parkingBrakeStatus);
            }

            manageShiftTransmission(openXCResponse.manualTrans, openXCResponse.transmissionGearPosition);

            parkingBreakActive.setOnCheckedChangeListener(checkListener);
            shiftTransmission.setOnCheckedChangeListener(checkListener);

            shiftTransmissionPositionDown.setOnClickListener(clickDownShift);
            shiftTransmissionPositionUp.setOnClickListener(clickUpShift);
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

        if (buttonOff != null) {
            buttonOff.setOnCheckedChangeListener(compoundIgnitionStatusListener);
        }
        if (buttonAccessory != null) {
            buttonAccessory.setOnCheckedChangeListener(compoundIgnitionStatusListener);
        }
        if (buttonRun != null) {
            buttonRun.setOnCheckedChangeListener(compoundIgnitionStatusListener);
        }
        if (buttonStart != null) {
            buttonStart.setOnCheckedChangeListener(compoundIgnitionStatusListener);
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

    private void manageShiftTransmission(boolean manualTrans, String transmissionGearPosition) {
        if (shiftTransmission != null && shiftTransmissionPosition != null) {
            shiftTransmission.setChecked(manualTrans);

            shiftTransmissionPositionDown.setEnabled(manualTrans);
            shiftTransmissionPositionUp.setEnabled(manualTrans);

            shiftTransmissionPosition.setText(transmissionGearPosition);
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
