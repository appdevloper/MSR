package com.digitalrupay.msrc.activitys.stockist;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.digitalrupay.msrc.R;
import com.digitalrupay.msrc.activitys.BaseActivity;
import com.digitalrupay.msrc.activitys.admin.AdminHomeActivity;
import com.digitalrupay.msrc.activitys.admin.EditAdminInventoryActivity;
import com.digitalrupay.msrc.backendServices.DataLoader;
import com.digitalrupay.msrc.dataModel.AdmIninventoryData;
import com.digitalrupay.msrc.dataModel.OperatorLoginData;
import com.digitalrupay.msrc.dataModel.StockistInventoryData;
import com.digitalrupay.msrc.dataModel.UpdateAdmininventoryData;
import com.digitalrupay.msrc.saveAppData.SaveAppData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Iterator;

public class EditStockInventoryActivity extends BaseActivity {
    StockistInventoryData stockistInventoryData;
    TextView Inventory_Name,Already_Available_Qty,Required_Quantity,Required_On,empname,tv_displaydate,Approved_Quantity;
    EditText Shipment_Charges,Remarks;
    Spinner Select_Status;
    String[] selectstatus;
    String status,emp_id,indent_id,inv_id,shp_date,shp_remarks,AvailableQty;
    RelativeLayout selectdate;
    ImageView dateimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stock_inventory);
        stockistInventoryData = (StockistInventoryData) getIntent().getExtras().getSerializable("stockinventoryData");
        OperatorLoginData operatorCode=null;
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
        empname=(TextView)findViewById(R.id.empname);
        String getempname=stockistInventoryData.getemp_first_name()+" "+stockistInventoryData.getemp_last_name();
        empname.setText(getempname+" ( "+stockistInventoryData.getind_invoice_id()+" )");
        emp_id=operatorCode.getemp_id();
        indent_id=stockistInventoryData.getindent_id();
        inv_id=stockistInventoryData.getinv_id();
        selectdate=(RelativeLayout)findViewById(R.id.selectdate);
        Inventory_Name=(TextView)findViewById(R.id.Inventory_Name);
        Approved_Quantity=(TextView)findViewById(R.id.Approved_Quantity);
        Already_Available_Qty=(TextView)findViewById(R.id.Already_Available_Qty);
        Required_Quantity=(TextView)findViewById(R.id.Required_Quantity);
        Required_On=(TextView)findViewById(R.id.Required_On);
        dateimage=(ImageView)findViewById(R.id.dateimage);
        tv_displaydate=(TextView) findViewById(R.id.tv_displaydate);
        Shipment_Charges=(EditText)findViewById(R.id.Shipment_Charges);
        Remarks=(EditText)findViewById(R.id.Remarks);

        Select_Status=(Spinner)findViewById(R.id.Select_Status);
        selectstatus=getResources().getStringArray(R.array.Stockarray);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_complaint,selectstatus);
        Select_Status.setAdapter(adapter);
        Select_Status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String getpositionname=selectstatus[position];
                if(getpositionname.equalsIgnoreCase("Shipped")){
                    status="3";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Intent dataIntent = new Intent(EditStockInventoryActivity.this, DataLoader.class);
        Messenger dataMessenger = new Messenger(EHandler);
        dataIntent.putExtra("MESSENGER", dataMessenger);
        dataIntent.putExtra("type", DataLoader.DataType.stockist_inventory_edit.ordinal());
        dataIntent.putExtra("empID", emp_id);
        dataIntent.putExtra("indentid", indent_id);
        startService(dataIntent);
    }
    public void openDateCalendar(View view) {
        int mYear, mMonth, mDay, mHour, mMinute;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                        dateimage.setVisibility(View.GONE);
                        tv_displaydate.setVisibility(View.VISIBLE);
                        tv_displaydate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
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

    private void setData(UpdateAdmininventoryData admIninventoryData) {
        Inventory_Name.setText(admIninventoryData.getname());
        Already_Available_Qty.setText(AvailableQty);
        Required_Quantity.setText(admIninventoryData.getrequired_qty());
        Required_On.setText(admIninventoryData.getrequired_date());
        Approved_Quantity.setText(admIninventoryData.getapproved_qty());

    }
    public void update(View view){
        shp_remarks=Remarks.getText().toString().replace(" ","%20");
        shp_date=tv_displaydate.getText().toString();
        Intent dataIntent = new Intent(EditStockInventoryActivity.this, DataLoader.class);
        Messenger dataMessenger = new Messenger(rHandler);
        dataIntent.putExtra("MESSENGER", dataMessenger);
        dataIntent.putExtra("type", DataLoader.DataType.stockist_inv_update.ordinal());
        dataIntent.putExtra("empID", emp_id);
        dataIntent.putExtra("indentid", indent_id);
        dataIntent.putExtra("inv_id", inv_id);
        dataIntent.putExtra("status", status);
        dataIntent.putExtra("inward_remarks", shp_remarks);
        dataIntent.putExtra("shp_date",shp_date);
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
                    Dialog dialog=new Dialog(EditStockInventoryActivity.this);
                    dialog.setContentView(R.layout.dialogcomplaint);
                    dialog.setCancelable(false);
                    TextView textView=(TextView)dialog.findViewById(R.id.successfully);
                    textView.setText("Successfully Update");
                    Button button=(Button) dialog.findViewById(R.id.Redirecttodashboard);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(EditStockInventoryActivity.this,StockistHomeActivity.class);
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
