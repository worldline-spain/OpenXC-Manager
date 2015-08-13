package com.worldline.openxcmanager;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;

import com.worldline.openxcmanagers.sdk.ApiClient;
import com.worldline.openxcmanagers.sdk.ApiClientProvider;
import com.worldline.openxcmanagers.sdk.OpenXCResponse;

import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements ApiClientPresenter.ApiClientPresenterCallback {

    private SeekBar seekBarSteeringWheelAngle;
    private SeekBar seekBarAcceleratorPercentPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        ApiClientPresenter presenter = new ApiClientPresenter();
        presenter.init(this);
    }

    private void findViews() {
        seekBarSteeringWheelAngle = (SeekBar) findViewById(R.id.steering_wheel_angle);
        seekBarAcceleratorPercentPercentage = (SeekBar) findViewById(R.id.accelerator_percent_percentage);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void steeringWheelAngle(int wheelAngle) {
        if (seekBarSteeringWheelAngle != null) {
            seekBarSteeringWheelAngle.setProgress(wheelAngle);
        }
    }

    @Override
    public void acceleratorPercentPercentage(int acceleratorPedalPosition) {
        if (seekBarAcceleratorPercentPercentage != null) {
            seekBarAcceleratorPercentPercentage.setProgress(acceleratorPedalPosition);
        }
    }
}
