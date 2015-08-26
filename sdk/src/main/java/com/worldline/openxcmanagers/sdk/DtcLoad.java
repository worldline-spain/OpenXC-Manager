package com.worldline.openxcmanagers.sdk;

import android.content.Context;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by a593310 on 18/08/2015.
 */
public class DtcLoad {

    private Realm realm = null;
    private Context context;

    public DtcLoad(Context context) {
        this.context = context;
        instanceRealm(context);
    }

    public void loadDTCFromCSV() {
        instanceRealm(context);

        RealmResults<DtcVO> all = realm.where(DtcVO.class).findAll();

        if (all != null && all.isEmpty()) {
            InputStream inputStream = context.getResources().openRawResource(R.raw.dtc_codes);
            CSVFile csvFile = new CSVFile(inputStream);
            List<String[]> scoreList = csvFile.read();
            saveInDataBase(scoreList);
        }
    }

    private void saveInDataBase(List<String[]> scoreList) {
        try {
            realm.beginTransaction();
            for (String[] scoreData : scoreList) {
                DtcVO dtcVO = new DtcVO();
                String dtcs = Arrays.toString(scoreData);
                String[] split = dtcs.split(",");
                String code = split[0];
                code = code.replace("[", "");
                dtcVO.setDtcCode(code);
                String description = split[1];
                description = description.replace("]", "");
                dtcVO.setDescription(description);
                realm.copyToRealmOrUpdate(dtcVO);
            }

            realm.commitTransaction();
            realm.close();
            realm = null;
        } catch (Exception e) {
            Log.e("DtcLoad error ", e.toString());
        }
    }

    public List<DtcVO> getDtcCodes() {
        instanceRealm(context);
        RealmResults<DtcVO> all = realm.where(DtcVO.class).findAllSorted("activate", false);
        return new ArrayList<>(all);
    }

    private void instanceRealm(Context context) {
        if (realm == null) {
            this.realm = Realm.getInstance(context.getApplicationContext());
        }
    }

    public void modifyDtc(DtcVO dtcVO, int active) {
        instanceRealm(context);
        realm.beginTransaction();

        DtcVO dtc = new DtcVO();
        dtc.setActivate(active);
        dtc.setDtcCode(dtcVO.getDtcCode());
        dtc.setDescription(dtcVO.getDescription());

        realm.copyToRealmOrUpdate(dtc);

        realm.commitTransaction();
        realm.close();
        realm = null;
    }
}
