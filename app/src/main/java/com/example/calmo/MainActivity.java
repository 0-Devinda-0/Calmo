
package com.example.calmo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, SignIn.class));
                finish();
            }
        }, 3000);

//        scheduleJob();
    }

//    public void scheduleJob(){
//
//        ComponentName jobService = new ComponentName(this, MonthlyJobService.class);
//        JobInfo jobInfo = new JobInfo.Builder(123, jobService)
//                .setPeriodic(1000L *60*60*24*30) // 2592000000 milliseconds = 30 days (monthly interval)
//                .build();
//        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
//        int resultCode = jobScheduler.schedule(jobInfo);
//        if (resultCode == jobScheduler.RESULT_SUCCESS){
//            Log.d(TAG,"JOB Done");
//            CustomToast.showDoneToast(getApplicationContext(),"done", Toast.LENGTH_LONG);
//        }else{
//            CustomToast.showErrorToast(getApplicationContext(),"failed", Toast.LENGTH_LONG);
//        }
//
//        jobScheduler.schedule(jobInfo);
//    }
//
//    public void cancelJob(View v){
//        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
//        jobScheduler.cancel(123);
//        Log.d(TAG,"JOB canceled");
//    }
}