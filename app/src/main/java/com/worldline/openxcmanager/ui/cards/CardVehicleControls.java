package com.worldline.openxcmanager.ui.cards;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.worldline.openxcmanager.R;
import com.worldline.openxcmanager.ui.cards.base.BaseCardView;
import com.worldline.openxcmanager.ui.cards.base.CardOpenXC;
import com.worldline.openxcmanagers.sdk.OpenXCResponse;

/**
 * Created by a557114 on 14/08/2015.
 */
public class CardVehicleControls extends CardOpenXC {

    private SeekBar seekBarSteeringWheelAngle;
    private SeekBar seekBarAcceleratorPercentPercentage;
    private SeekBar seekBarBreakPercentPercentage;

    public CardVehicleControls(Context context) {
        super(context);
        init();
    }

    public CardVehicleControls(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CardVehicleControls(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.card_vehicle_controls, this);

        Toolbar toolbar_vehicle_controls = (Toolbar) findViewById(R.id.toolbar);
        toolbar_vehicle_controls.setTitle(R.string.vehicle_controls_title);

        seekBarSteeringWheelAngle = (SeekBar) findViewById(R.id.steering_wheel_angle);
        seekBarAcceleratorPercentPercentage = (SeekBar) findViewById(R.id.accelerator_percent_percentage);
        seekBarBreakPercentPercentage = (SeekBar) findViewById(R.id.break_percent_percentage);
    }

    @Override
    public void setData(OpenXCResponse openXCResponse) {
        if (openXCResponse != null) {
            steeringWheelAngle(openXCResponse.steeringWheelAngle + 600);
            acceleratorPercentPercentage(openXCResponse.acceleratorPedalPosition);
            breakPercentPercentage(openXCResponse.brake);
        }
    }

    public void steeringWheelAngle(int wheelAngle) {
        if (seekBarSteeringWheelAngle != null) {
            seekBarSteeringWheelAngle.setProgress(wheelAngle);
        }
    }

    public void acceleratorPercentPercentage(int acceleratorPedalPosition) {
        if (seekBarAcceleratorPercentPercentage != null) {
            seekBarAcceleratorPercentPercentage.setProgress(acceleratorPedalPosition);
        }
    }

    public void breakPercentPercentage(int breakPedalPosition) {
        if (seekBarBreakPercentPercentage != null) {
            seekBarBreakPercentPercentage.setProgress(breakPedalPosition);
        }
    }
}
