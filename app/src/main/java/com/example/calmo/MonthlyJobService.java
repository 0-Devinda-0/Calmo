package com.example.calmo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MonthlyJobService extends BroadcastReceiver {
    DataBaseHelper calmoDb;
    private boolean isJobDone;
    private String TAG = "MainActivityTag";
    @Override
    public void onReceive(Context context, Intent intent) {
        calmoDb = new DataBaseHelper(context);
        isJobDone = calmoDb.monthlyStateChange();
        Log.i(TAG,"JOB Done");
    }

}
