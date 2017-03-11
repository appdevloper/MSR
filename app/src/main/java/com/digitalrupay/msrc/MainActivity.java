package com.digitalrupay.msrc;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalrupay.msrc.activitys.EMPLoginActivity;
import com.digitalrupay.msrc.activitys.admin.AdminHomeActivity;
import com.digitalrupay.msrc.activitys.emp.EMPHomeActivity;
import com.digitalrupay.msrc.activitys.SearchOperatorCode;
import com.digitalrupay.msrc.activitys.stockist.StockistHomeActivity;
import com.digitalrupay.msrc.dataModel.OperatorLoginData;
import com.digitalrupay.msrc.saveAppData.SaveAppData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView zoom = (ImageView) findViewById(R.id.logo);
        final TextView ZoomOut=(TextView)findViewById(R.id.ZoomOut);
        final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        zoom.startAnimation(zoomAnimation);
        Animation animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_out);
        ZoomOut.startAnimation(animZoomOut);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (SaveAppData.getSessionDataInstance().getOperatorData() != null) {
                    if(SaveAppData.getSessionDataInstance().getOperatorLoginData() != null) {
                        OperatorLoginData operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
                        String type = operatorCode.getuser_type();
                        if (type.equalsIgnoreCase("4")) {
                            Intent intent = new Intent(MainActivity.this, EMPHomeActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                        } else if (type.equalsIgnoreCase("1")) {
                            Intent intent = new Intent(MainActivity.this, AdminHomeActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                        } else if (type.equalsIgnoreCase("2")) {
                            Intent intent = new Intent(MainActivity.this, StockistHomeActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                        }
                    }else{
                        Intent intent=new Intent(MainActivity.this,EMPLoginActivity.class);
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                } else {
                    redirectToLoginActivity();
                }
            }
            }, 1500);
        }
    private void redirectToLoginActivity() {
        Intent intent=new Intent(MainActivity.this,SearchOperatorCode.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

}

