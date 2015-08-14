package com.worldline.openxcmanager.ui.cards.base;

import android.content.Context;
import android.util.AttributeSet;

import com.worldline.openxcmanagers.sdk.OpenXCResponse;

/**
 * Created by a557114 on 14/08/2015.
 */
public abstract class CardOpenXC extends BaseCardView {
    public CardOpenXC(Context context) {
        super(context);
    }

    public CardOpenXC(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardOpenXC(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void setData(OpenXCResponse openXCResponse);
}
