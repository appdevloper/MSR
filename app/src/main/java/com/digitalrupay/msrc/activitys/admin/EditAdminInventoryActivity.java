package com.digitalrupay.msrc.activitys.admin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.digitalrupay.msrc.R;
import com.digitalrupay.msrc.activitys.BaseActivity;
import com.digitalrupay.msrc.activitys.emp.UpdateComplaintActivity;
import com.digitalrupay.msrc.adapter.AdminListAdapter;
import com.digitalrupay.msrc.backendServices.DataLoader;
import com.digitalrupay.msrc.dataModel.AdmIninventoryData;
import com.digitalrupay.msrc.dataModel.OperatorLoginData;
import com.digitalrupay.msrc.dataModel.UpdateAdmininventoryData;
import com.digitalrupay.msrc.saveAppData.SaveAppData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class EditAdminInventoryActivity extends BaseActivity {
    AdmIninventoryData admIninventoryData;
    TextView Inventory_Name,Already_Available_Qty,Required_Quantity,Required_On,empname;
    EditText Approved_Quantity,Shipment_Charges,Remarks;
    Spinner Select_Status;
    String[] selectstatus;
    String status,emp_id,indent_id,inv_id,approved_qty,shp_charges,inward_remarks,AvailableQty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin_inventory);
        admIninventoryData = (AdmIninventoryData) getIntent().getExtras().getSerializable("AdmIninventoryData");
        OperatorLoginData operatorCode=null;
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
        empname=(TextView)findViewById(R.id.empname);
        String getempname=admIninventoryData.getemp_first_name()+" "+admIninventoryData.getemp_last_name();
        empname.setText(getempname+" ( "+admIninventoryData.getind_invoice_id()+" )");
        emp_id=operatorCode.getemp_id();
        indent_id=admIninventoryData.getindent_id();
        inv_id=admIninventoryData.getinv_id();

        Inventory_Name=(TextView)findViewById(R.id.Inventory_Name);
        Already_Available_Qty=(TextView)findViewById(R.id.Already_Available_Qty);
        Required_Quantity=(TextView)findViewById(R.id.Required_Quantity);
        Required_On=(TextView)findViewById(R.id.Required_On);

        Approved_Quantity=(EditText)findViewById(R.id.Approved_Quantity);
        Shipment_Charges=(EditText)findViewById(R.id.Shipment_Charges);
        Remarks=(EditText)findViewById(R.id.Remarks);

        Select_Status=(Spinner)findViewById(R.id.Select_Status);
        selectstatus=getResources().getStringArray(R.array.SelectStatusarray);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_complaint,selectstatus);
        Select_Status.setAdapter(adapter);

        Intent dataIntent = new Intent(EditAdminInventoryActivity.this, DataLoader.class);
        Messenger dataMessenger = new Messenger(EHandler);
        dataIntent.putExtra("MESSENGER", dataMessenger);
        dataIntent.putExtra("type", DataLoader.DataType.admin_inventory_edit.ordinal());
        dataIntent.putExtra("empID", emp_id);
        dataIntent.putExtra("indentid", indent_id);
        startService(dataIntent);

        Select_Status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String getselect=selectstatus[position];
                if(getselect.equalsIgnoreCase("Approved")){
                    status="1";
                }else if (getselect.equalsIgnoreCase("Rejected")){
                    status="0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private Handler EHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle   bundle = msg.getData();
            String response = bundle.getString("data");
            try{
                JSONObject responseObj = new JSONObject(response);
                String message = responseObj.getString("message");
                if (message.equalsIgnoreCase("success")) {
                    if(!responseObj.isNull("AvailableQty")) {
                        AvailableQty = responseObj.getString("AvailableQty");
                    }else {
                        AvailableQty="0";
                    }
                    Iterator<String> keys = responseObj.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            UpdateAdmininventoryData admIninventoryData = new Gson().fromJson(responseObj.getJSONObject(key).toString(),new TypeToken<UpdateAdmininventoryData>() {}.getType());
                            setData(admIninventoryData);
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    public void setData(UpdateAdmininventoryData admIninventoryData){
        Inventory_Name.setText(admIninventoryData.getname());
        Already_Available_Qty.setText(AvailableQty);
        Required_Quantity.setText(admIninventoryData.getrequired_qty());
        Required_On.setText(admIninventoryData.getrequired_date());
    }
    public void update(View view){
        inward_remarks=Remarks.getText().toString().replace(" ","%20");
        approved_qty=Approved_Quantity.getText().toString().trim();
        shp_charges=Shipment_Charges.getText().toString();

        Intent dataIntent = new Intent(EditAdminInventoryActivity.this, DataLoader.class);
        Messenger dataMessenger = new Messenger(rHandler);
        dataIntent.putExtra("MESSENGER", dataMessenger);
        dataIntent.putExtra("type", DataLoader.DataType.admin_inv_update.ordinal());
        dataIntent.putExtra("empID", emp_id);
        dataIntent.putExtra("indentid", indent_id);
        dataIntent.putExtra("inv_id", inv_id);
        dataIntent.putExtra("status", status);
        dataIntent.putExtra("approved_qty", approved_qty);
        dataIntent.putExtra("shp_charges", shp_charges);
        dataIntent.putExtra("inward_remarks", inward_remarks);
        startService(dataIntent);
    }
    private Handler rHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle   bundle = msg.getData();
            String response = bundle.getString("data");
            try{
                JSONObject responseObj = new JSONObject(response);
                String message = responseObj.getString("message");
                if (message.equalsIgnoreCase("success")) {
                    Dialog dialog=new Dialog(EditAdminInventoryActivity.this);
                    dialog.setContentView(R.layout.dialogcomplaint);
                    dialog.setCancelable(false);
                    TextView textView=(TextView)dialog.findViewById(R.id.successfully);
                    textView.setText("Successfully Update");
                    Button button=(Button) dialog.findViewById(R.id.Redirecttodashboard);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(EditAdminInventoryActivity.this,AdminHomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    dialog.show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };

}
