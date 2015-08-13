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

public class MainActivity extends AppCompatActivity implements Callback<OpenXCResponse> {

    private SeekBar seekBarSteeringWheelAngle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiClientProvider apiClientProvider = new ApiClientProvider() {

            @Override
            protected String getEndpoint() {
                return "http://192.168.2.115";
            }

            @Override
            public int getPort() {
                return 50000;
            }
        };

        ApiClient.init(apiClientProvider);

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ApiClient.getInstance().getData(MainActivity.this);
                handler.postDelayed(this, 200);
            }
        };
        handler.post(runnable);

        findViews();
    }

    private void findViews() {
        seekBarSteeringWheelAngle = (SeekBar) findViewById(R.id.steering_wheel_angle);
    }

    @Override
    public void success(OpenXCResponse openXCResponse, Response response) {
        seekBarSteeringWheelAngle.setProgress(getSteeringWheelAngleOffsetFromOpenXC(openXCResponse.steeringWheelAngle));
    }

    private int getSteeringWheelAngleOffsetFromOpenXC(int steeringWheelAngle) {
        return steeringWheelAngle + 600;
    }

    @Override
    public void failure(RetrofitError error) {

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
}
