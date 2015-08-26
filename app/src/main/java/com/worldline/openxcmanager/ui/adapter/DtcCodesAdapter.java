package com.worldline.openxcmanager.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.worldline.openxcmanager.R;
import com.worldline.openxcmanagers.sdk.ApiClient;
import com.worldline.openxcmanagers.sdk.DtcVO;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by a557114 on 26/08/2015.
 */
public class DtcCodesAdapter extends RecyclerView.Adapter<DtcCodesAdapter.DtcHolder> {
    private LayoutInflater inflater;
    private List<DtcVO> allDtcCodes;
    private DtcCallback dtcCallback;

    public DtcCodesAdapter(LayoutInflater inflater, List<DtcVO> allDtcCodes) {
        this.inflater = inflater;
        this.allDtcCodes = allDtcCodes;
    }

    @Override
    public DtcHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DtcHolder(inflater.inflate(R.layout.two_line_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(DtcHolder holder, int position) {
        DtcVO dtcVO = allDtcCodes.get(position);

        holder.text1.setText(dtcVO.getDtcCode());
        holder.text2.setText(dtcVO.getDescription());
        holder.checkboxEnabled = false;
        holder.checkbox.setChecked(dtcVO.getActivate() == 1);
        holder.checkboxEnabled = true;
    }

    @Override
    public int getItemCount() {
        return allDtcCodes != null ? allDtcCodes.size() : 0;
    }

    public void setDtcCallback(DtcCallback dtcCallback) {
        this.dtcCallback = dtcCallback;
    }

    public class DtcHolder extends RecyclerView.ViewHolder {
        private final TextView text1;
        private final TextView text2;
        private final CheckBox checkbox;
        public boolean checkboxEnabled;

        public DtcHolder(View itemView) {
            super(itemView);
            text1 = (TextView) itemView.findViewById(android.R.id.text1);
            text2 = (TextView) itemView.findViewById(android.R.id.text2);
            checkbox = (CheckBox) itemView.findViewById(android.R.id.checkbox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DtcVO dtcVO = allDtcCodes.get(getAdapterPosition());

                    if (dtcVO != null && dtcCallback != null) {
                        if (dtcVO.getActivate() == 0) {
                            dtcCallback.sendDTC(dtcVO);
                        } else if (dtcVO.getActivate() == 1) {
                            dtcCallback.cancelDTC(dtcVO);
                        }
                    }
                }
            });

            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    DtcVO dtcVO = allDtcCodes.get(getAdapterPosition());

                    if (checkboxEnabled && dtcVO != null && dtcCallback != null) {
                        if (isChecked) {
                            dtcCallback.sendDTC(dtcVO);
                        } else {
                            dtcCallback.cancelDTC(dtcVO);
                        }
                    }
                }
            });
        }
    }

    public interface DtcCallback {
        void sendDTC(DtcVO dtcVO);

        void cancelDTC(DtcVO dtcVO);
    }
}
