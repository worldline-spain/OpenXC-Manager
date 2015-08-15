package com.worldline.openxcmanager.ui.cards;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.worldline.openxcmanager.R;
import com.worldline.openxcmanager.ui.cards.base.CardOpenXC;
import com.worldline.openxcmanagers.sdk.OpenXCResponse;

/**
 * Created by a557114 on 15/08/2015.
 */
public class CardLocation extends CardOpenXC {

    private ImageView imageLocation;

    private double latitude;
    private double longitude;

    public CardLocation(Context context) {
        super(context);
        init();
    }

    public CardLocation(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CardLocation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.card_vehicle_location, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.vehicle_location_title);

        imageLocation = (ImageView) findViewById(R.id.image_location);
    }

    @Override
    public void setData(OpenXCResponse openXCResponse) {
        if (imageLocation != null) {
            if (this.latitude != openXCResponse.latitude || this.longitude != openXCResponse.longitude) {
                this.latitude = openXCResponse.latitude;
                this.longitude = openXCResponse.longitude;

                Uri uri = buildUri(openXCResponse.latitude, openXCResponse.longitude);

                Glide.with(getContext()).load(uri).into(imageLocation);
            }
        }
    }

    private Uri buildUri(double latitude, double longitude) {
        // https://maps.googleapis.com/maps/api/staticmap?size=1000x600&markers=color:blue%7C42.292834,-83.237275&zoom=17

        int width = getResources().getInteger(R.integer.map_image_width);
        int height = getResources().getInteger(R.integer.map_image_height);
        int scale = getResources().getInteger(R.integer.map_image_scale);

        Uri.Builder builder = Uri.EMPTY.buildUpon();
        builder.scheme("https")
                .authority("maps.googleapis.com")
                .appendPath("maps")
                .appendPath("api")
                .appendPath("staticmap")
                .encodedQuery("markers=color:blue%7C" + latitude + "," + longitude)
                .appendQueryParameter("size", width + "x" + height)
                .appendQueryParameter("zoom", "16")
                .appendQueryParameter("scale", String.valueOf(scale));

        return builder.build();
    }
}
