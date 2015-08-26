package com.worldline.openxcmanager.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    }

    @Override
    public int getItemCount() {
        return allDtcCodes != null ? allDtcCodes.size() : 0;
    }

    public class DtcHolder extends RecyclerView.ViewHolder{
        private final TextView text1;
        private final TextView text2;

        public DtcHolder(View itemView) {
            super(itemView);
            text1 = (TextView) itemView.findViewById(android.R.id.text1);
            text2 = (TextView) itemView.findViewById(android.R.id.text2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DtcVO dtcVO = allDtcCodes.get(getAdapterPosition());
                    Callback<Response> callback =  new Callback<Response>() {
                        @Override
                        public void success(Response response, Response response2) {

                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    };
                    ApiClient.getInstance().customMessage(dtcVO.getDtcCode(), "true", "DTC_ERROR", callback);
                }
            });
        }
    }
}
