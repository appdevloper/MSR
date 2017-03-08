package com.digitalrupay.msrc.activitys.emp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.digitalrupay.msrc.R;
import com.digitalrupay.msrc.activitys.BaseActivity;
import com.digitalrupay.msrc.activitys.EMPLoginActivity;
import com.digitalrupay.msrc.activitys.SearchOperatorCode;
import com.digitalrupay.msrc.activitys.admin.AdminHomeActivity;
import com.digitalrupay.msrc.activitys.stockist.StockistHomeActivity;
import com.digitalrupay.msrc.backendServices.GPSTracker;
import com.digitalrupay.msrc.backendServices.SendGPSServicesLocation;
import com.digitalrupay.msrc.dataModel.OperatorLoginData;
import com.digitalrupay.msrc.saveAppData.SaveAppData;

public class EMPHomeActivity extends AppCompatActivity {
    TextView empname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emphome);
        OperatorLoginData operatorCode=null;
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
        empname=(TextView)findViewById(R.id.empname);
        String getempname=operatorCode.getemp_first_name()+" "+operatorCode.getemp_last_name();
        empname.setText("Hello "+getempname);
        Intent intent=new Intent(this,SendGPSServicesLocation.class);
        startService(intent);
    }
    public void goInventory(View view){
        Intent intent=new Intent(this,InventoryActivity.class);
        startActivity(intent);
    }
    public void goComplaints(View view){
        Intent intent=new Intent(this,ComplaintsActivity.class);
        startActivity(intent);
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
    private void logout() {
        SaveAppData.saveOperatorData(null);
        SaveAppData.saveOperatorLoginData(null);
        Intent login = new Intent(this, SearchOperatorCode.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(login);
        finish();
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
    private void home() {
        OperatorLoginData operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
        String type = operatorCode.getuser_type();
        if (type.equalsIgnoreCase("4")) {
            Intent intent = new Intent(this, EMPHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (type.equalsIgnoreCase("1")) {
            Intent intent = new Intent(this, AdminHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (type.equalsIgnoreCase("2")) {
            Intent intent = new Intent(this, StockistHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        finish();
    }
}
