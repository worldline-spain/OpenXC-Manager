package com.worldline.openxcmanager.ui.cards.base;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.worldline.openxcmanager.R;

/**
 * Created by a557114 on 14/08/2015.
 */
public class BaseCardView extends CardView {
    public BaseCardView(Context context) {
        super(context);
        initCard();
    }

    public BaseCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCard();
    }

    public BaseCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCard();
    }

    private void initCard() {
        this.setRadius(getResources().getDimensionPixelOffset(R.dimen.gapSmall));
        this.setUseCompatPadding(false);
        this.setPreventCornerOverlap(true);
    }

}
