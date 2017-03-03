package com.digitalrupay.msrc.activitys.emp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.digitalrupay.msrc.R;
import com.digitalrupay.msrc.activitys.BaseActivity;
import com.digitalrupay.msrc.dataModel.OperatorLoginData;
import com.digitalrupay.msrc.saveAppData.SaveAppData;

public class InventoryActivity extends BaseActivity {
    TextView empname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        OperatorLoginData operatorCode=null;
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
        empname=(TextView)findViewById(R.id.in_empname);
        String getempname=operatorCode.getemp_first_name()+" "+operatorCode.getemp_last_name();
        empname.setText("Hello "+getempname);
    }
    public void goInventorylist(View view){
        Intent intent=new Intent(this,InventoryListActivity.class);
        startActivity(intent);
    }
    public void goIndent(View view){
        Intent intent=new Intent(this,IndentRaisingActivity.class);
        startActivity(intent);
    }
}
