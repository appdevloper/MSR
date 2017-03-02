package com.digitalrupay.msrc.backendServices;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import com.digitalrupay.msrc.dataModel.OperatorLoginData;
import com.digitalrupay.msrc.saveAppData.SaveAppData;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sridhar on 2/27/2017.
 */

public class SendGPSServicesLocation extends Service {
    GPSTracker gps;
    private Timer mTimer1;
    private TimerTask mTt1;
    private Handler mTimerHandler = new Handler();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTimer();
        return START_STICKY;
    }
    private void stopTimer(){
        if(mTimer1 != null){
            mTimer1.cancel();
            mTimer1.purge();
            turnGPSOff();
        }
    }
    private void startTimer(){
        mTimer1 = new Timer();
        mTt1 = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run(){
                        //TODO
                        OperatorLoginData operatorCode = null;
                        operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
                       String getempID = operatorCode.getemp_id();
                        double latitude=0.0,longitude=0.0;
                        if(gps.canGetLocation()){
                            latitude = gps.getLatitude();
                            longitude  = gps.getLongitude();
                            if(latitude==0.0&&longitude==0.0){
                                gps = new GPSTracker(SendGPSServicesLocation.this);
                            }else {
                                Log.e("GetLocation","latitude :- "+latitude+"\n longitude :- "+longitude);
                                Intent dataIntent = new Intent(SendGPSServicesLocation.this, DataLoader.class);
                                Messenger dataMessenger = new Messenger(hEMPGPSLOC);
                                dataIntent.putExtra("MESSENGER", dataMessenger);
                                dataIntent.putExtra("type", DataLoader.DataType.EMPGPSLOC.ordinal());
                                dataIntent.putExtra("gps_lang",""+longitude);
                                dataIntent.putExtra("gps_lat",""+latitude);
                                dataIntent.putExtra("emp_id",getempID);
                                startService(dataIntent);
                            }
                        }else {
                            turnGPSOn();
                        }
                    }
                });
            }
        };
        mTimer1.schedule(mTt1, 1, 15000);
    }
    private Handler hEMPGPSLOC=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String response = bundle.getString("data");
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
        stopSelf();
    }
    private void turnGPSOn(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }

    private void turnGPSOff(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(provider.contains("gps")){ //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }
}
