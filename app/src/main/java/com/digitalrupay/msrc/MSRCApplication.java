package com.digitalrupay.msrc;

import android.app.Application;
import android.content.Context;

import com.digitalrupay.msrc.backendServices.ConnectivityReceiver;

/**
 * Created by sridhar on 2/8/2017.
 */

public class MSRCApplication extends Application implements ConnectivityReceiver.ConnectivityReceiverListener{
    private static Context appContext;
    private static MSRCApplication mInstance;
    boolean isConnected;
    public static String EMPStatusCode="";
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        appContext = getApplicationContext();
        setAppContext(getApplicationContext());
        isConnected = ConnectivityReceiver.isConnected();
    }
    public static Context getAppContext() {
        return appContext;
    }

    private static void setAppContext(Context appContext) {
        MSRCApplication.appContext = appContext;
    }
    public static synchronized MSRCApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
    public void onNetworkConnectionChanged(boolean Connected) {
        isConnected=Connected;
    }
}
