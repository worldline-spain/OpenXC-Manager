package com.worldline.openxcmanager.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.worldline.openxcmanager.R;
import com.worldline.openxcmanager.ui.cards.CardVehicleMilDtc;
import com.worldline.openxcmanager.ui.cards.base.CardOpenXC;
import com.worldline.openxcmanagers.sdk.OpenXCResponse;

/**
 * Created by a557114 on 14/08/2015.
 */
public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardHolder> {

    private static final int CARD_VEHICLE_CONTROLS = 1;
    private static final int CARD_VEHICLE_STATUS = 2;
    private static final int CARD_VEHICLE_MIL_DTC = 3;

    private LayoutInflater inflater;
    private OpenXCResponse openXcData;
    private CardVehicleMilDtc.MilDtcCallback milDtcCallback;

    private RequestListener requestListener;

    public CardsAdapter(LayoutInflater inflater, RequestListener requestListener, CardVehicleMilDtc.MilDtcCallback milDtcCallback) {
        this.inflater = inflater;
        this.requestListener = requestListener;
        this.milDtcCallback = milDtcCallback;
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            default:
            case CARD_VEHICLE_CONTROLS:
                return new CardHolder(inflater.inflate(R.layout.card_vehicle_controls_row, viewGroup, false));
            case CARD_VEHICLE_STATUS:
                return new CardHolder(inflater.inflate(R.layout.card_vehicle_status_row, viewGroup, false));
            case CARD_VEHICLE_MIL_DTC:
                View view = inflater.inflate(R.layout.card_vehicle_mil_dtc_row, viewGroup, false);
                CardVehicleMilDtc cardVehicleMilDtc = (CardVehicleMilDtc) view.findViewById(R.id.card);
                cardVehicleMilDtc.setMilDtcCallback(milDtcCallback);
                return new CardHolder(view);
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
        return openXcData != null ? 3 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            default:
            case 0:
                return CARD_VEHICLE_CONTROLS;
            case 1:
                return CARD_VEHICLE_STATUS;
            case 2:
                return CARD_VEHICLE_MIL_DTC;
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
            card.setRequestListener(requestListener);
        }
    }

    public interface RequestListener {
        void sendCustomMessage(String key, String vaalue, String event);

        void postData(String key, String value);
    }
}
