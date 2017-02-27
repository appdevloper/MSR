package com.digitalrupay.msrc.activitys.emp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.digitalrupay.msrc.R;
import com.digitalrupay.msrc.activitys.BaseActivity;
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
    }
    public void goInventory(View view){
        Intent intent=new Intent(this,InventoryActivity.class);
        startActivity(intent);
    }
    public void goComplaints(View view){
        Intent intent=new Intent(this,ComplaintsActivity.class);
        startActivity(intent);
    }
}
