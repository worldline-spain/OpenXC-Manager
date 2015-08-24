package com.worldline.openxcmanager.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.worldline.openxcmanager.R;
import com.worldline.openxcmanager.ui.cards.base.CardOpenXC;
import com.worldline.openxcmanagers.sdk.OpenXCResponse;

/**
 * Created by a557114 on 14/08/2015.
 */
public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardHolder> {

    private static final int CARD_VEHICLE_CONTROLS = 1;
    private static final int CARD_VEHICLE_STATUS = 2;

    private LayoutInflater inflater;
    private OpenXCResponse openXcData;

    public CardsAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            default:
            case CARD_VEHICLE_CONTROLS:
                return new CardHolder(inflater.inflate(R.layout.card_vehicle_controls_row, viewGroup, false));
            case CARD_VEHICLE_STATUS:
                return new CardHolder(inflater.inflate(R.layout.card_vehicle_status_row, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(CardHolder cardHolder, int position) {
        if (openXcData != null) {
            cardHolder.card.setData(openXcData);
        }

    }

    @Override
    public int getItemCount() {
        return openXcData != null ? 2 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            default:
            case 0:
                return CARD_VEHICLE_CONTROLS;
            case 1:
                return CARD_VEHICLE_STATUS;
        }
    }

    public void setOpenXcData(OpenXCResponse openXcData) {
        this.openXcData = openXcData;
        notifyDataSetChanged();
    }

    public void clear() {
        this.openXcData = null;
        notifyDataSetChanged();
    }

    public class CardHolder extends RecyclerView.ViewHolder {
        private final CardOpenXC card;

        public CardHolder(View itemView) {
            super(itemView);
            card = (CardOpenXC) itemView.findViewById(R.id.card);
        }
    }
}
