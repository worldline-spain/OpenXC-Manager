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

    private SeekBar seekBarAcceleratorPercentPercentage;
    private SeekBar.OnSeekBarChangeListener seekBarAcceleratorListener;

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

        seekBarAcceleratorPercentPercentage = (SeekBar) findViewById(R.id.accelerator_percent_percentage);

        seekBarAcceleratorListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (requestListener != null) {
                    requestListener.postData("accelerator", String.valueOf(seekBar.getProgress()));
                }
            }
        };

        seekBarAcceleratorPercentPercentage.setOnSeekBarChangeListener(seekBarAcceleratorListener);

    }

    @Override
    public void setData(OpenXCResponse openXCResponse) {
        if (openXCResponse != null) {
            acceleratorPercentPercentage(openXCResponse.acceleratorPedalPosition);
        }
    }

    public void acceleratorPercentPercentage(int acceleratorPedalPosition) {
        if (seekBarAcceleratorPercentPercentage != null) {
            seekBarAcceleratorPercentPercentage.setOnSeekBarChangeListener(null);
            seekBarAcceleratorPercentPercentage.setProgress(acceleratorPedalPosition);
            seekBarAcceleratorPercentPercentage.setOnSeekBarChangeListener(seekBarAcceleratorListener);
        }
    }
}
