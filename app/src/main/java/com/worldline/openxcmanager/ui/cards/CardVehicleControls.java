package com.worldline.openxcmanager.ui.cards;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.worldline.openxcmanager.R;
import com.worldline.openxcmanager.ui.cards.base.CardOpenXC;
import com.worldline.openxcmanagers.sdk.OpenXCResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by a557114 on 14/08/2015.
 */
public class CardVehicleControls extends CardOpenXC {

    //private CenterSeekBar seekBarSteeringWheelAngle;
    private SeekBar seekBarAcceleratorPercentPercentage;
    //private SeekBar seekBarBreakPercentPercentage;
    private Callback<Response> responseCallback;
    private boolean widgetsEnabled = true;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.vehicle_controls_title);

//        seekBarSteeringWheelAngle = (CenterSeekBar) findViewById(R.id.steering_wheel_angle);
        seekBarAcceleratorPercentPercentage = (SeekBar) findViewById(R.id.accelerator_percent_percentage);
//        seekBarBreakPercentPercentage = (SeekBar) findViewById(R.id.break_percent_percentage);

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

//        seekBarSteeringWheelAngle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                widgetsEnabled = false;
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                int progress = seekBarSteeringWheelAngle.getOffsetProgress();
//                ApiClient.getInstance().postData("angle", String.valueOf(progress), responseCallback);
//            }
//        });

        seekBarAcceleratorPercentPercentage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                widgetsEnabled = false;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (requestListener != null) {
                    requestListener.postData("accelerator", String.valueOf(seekBar.getProgress()));
                }
            }
        });

//        seekBarBreakPercentPercentage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                widgetsEnabled = false;
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                ApiClient.getInstance().postData("brake", String.valueOf(seekBar.getProgress()), responseCallback);
//            }
//        });
    }

    @Override
    public void setData(OpenXCResponse openXCResponse) {
        if (openXCResponse != null && widgetsEnabled) {
            steeringWheelAngle(openXCResponse.steeringWheelAngle);
            acceleratorPercentPercentage(openXCResponse.acceleratorPedalPosition);
            breakPercentPercentage(openXCResponse.brake);
        }
    }

    public void steeringWheelAngle(int wheelAngle) {
//        if (seekBarSteeringWheelAngle != null) {
//            seekBarSteeringWheelAngle.setProgress(wheelAngle);
//        }
    }

    public void acceleratorPercentPercentage(int acceleratorPedalPosition) {
        if (seekBarAcceleratorPercentPercentage != null) {
            seekBarAcceleratorPercentPercentage.setProgress(acceleratorPedalPosition);
        }
    }

    public void breakPercentPercentage(int breakPedalPosition) {
//        if (seekBarBreakPercentPercentage != null) {
//            seekBarBreakPercentPercentage.setProgress(breakPedalPosition);
//        }
    }
}
