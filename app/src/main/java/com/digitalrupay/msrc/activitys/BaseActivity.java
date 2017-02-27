package com.digitalrupay.msrc.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.digitalrupay.msrc.MSRCApplication;
import com.digitalrupay.msrc.MainActivity;
import com.digitalrupay.msrc.activitys.admin.AdminHomeActivity;
import com.digitalrupay.msrc.activitys.emp.EMPHomeActivity;
import com.digitalrupay.msrc.activitys.stockist.StockistHomeActivity;
import com.digitalrupay.msrc.backendServices.ConnectivityReceiver;
import com.digitalrupay.msrc.dataModel.OperatorLoginData;
import com.digitalrupay.msrc.saveAppData.SaveAppData;

/**
 * Created by sridhar on 2/9/2017.
 */

public class BaseActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    boolean isConnected;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isConnected = ConnectivityReceiver.isConnected();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public void onNetworkConnectionChanged(boolean Connected) {
        isConnected = Connected;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void redirectToLoginActivity(View view) {
        AlertDialog logoutDialog = new AlertDialog.Builder(this).setTitle("Logout")
                .setMessage("Are you sure want to logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        logout();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false).show();
    }

    public void redirectToHomeActivity(View view) {
        AlertDialog homeDialog = new AlertDialog.Builder(this).setTitle("Home")
                .setMessage("Are you sure want to navigate to home screen?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        home();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false).show();
    }

    private void logout() {
        SaveAppData.saveOperatorData(null);
        SaveAppData.saveOperatorLoginData(null);
        Intent login = new Intent(this, SearchOperatorCode.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(login);
        finish();
    }

    private void home() {
        OperatorLoginData operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
        String type = operatorCode.getuser_type();
        if (type.equalsIgnoreCase("4")) {
            Intent intent = new Intent(BaseActivity.this, EMPHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (type.equalsIgnoreCase("1")) {
            Intent intent = new Intent(BaseActivity.this, AdminHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (type.equalsIgnoreCase("2")) {
            Intent intent = new Intent(BaseActivity.this, StockistHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        try {
            MSRCApplication.getInstance().setConnectivityListener(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
