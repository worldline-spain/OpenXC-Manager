package com.worldline.openxcmanager.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

import com.worldline.openxcmanager.R;

/**
 * Created by a557114 on 16/08/2015.
 */
public class CenterSeekBar extends SeekBar {

    private int offset = 0;

    public CenterSeekBar(Context context) {
        super(context);
        init(null);
    }

    public CenterSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CenterSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CenterSeekBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CenterSeekBar);
            setMin(a.getInteger(R.styleable.CenterSeekBar_csb_min, 0));
            a.recycle();
        }
    }

    public void setMin(int min) {
        if (min < 0) {
            min = min * -1;
        }

        setMax(getMax() + min);

        offset = min;
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress + offset);
    }

    public synchronized int getOffsetProgress() {
        return super.getProgress() - offset;
    }


}
