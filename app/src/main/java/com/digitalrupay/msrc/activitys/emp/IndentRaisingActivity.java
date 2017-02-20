package com.digitalrupay.msrc.activitys.emp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.digitalrupay.msrc.R;
import com.digitalrupay.msrc.activitys.BaseActivity;
import com.digitalrupay.msrc.adapter.SpinnerComplaintAdapter;
import com.digitalrupay.msrc.adapter.SpinnerInventoryAdapter;
import com.digitalrupay.msrc.backendServices.DataLoader;
import com.digitalrupay.msrc.dataModel.ComplaintData;
import com.digitalrupay.msrc.dataModel.InventoryItemsData;
import com.digitalrupay.msrc.dataModel.OperatorLoginData;
import com.digitalrupay.msrc.saveAppData.SaveAppData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class IndentRaisingActivity extends BaseActivity {

    TextView empname,selectdate;
    Spinner inventory_complaint,inventory_item;
    LinearLayout lay_remarks;
    boolean openremark;
    ImageView ic_calender;
    EditText Quality,remarks;
    ArrayList<ComplaintData> complaintsDataList = new ArrayList<>();
    ArrayList<InventoryItemsData> inventoryList;
    String getempID,inv_id,required_qty,indent_reason,required_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent_raising);
        openremark = false;

        OperatorLoginData operatorCode=null;
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();

        empname=(TextView)findViewById(R.id.in_empname);
        inventory_item=(Spinner)findViewById(R.id.inventory_item);
        inventory_complaint=(Spinner)findViewById(R.id.inventory_complaint);
        lay_remarks=(LinearLayout)findViewById(R.id.lay_remarks);
        selectdate=(TextView)findViewById(R.id.selectdate);
        ic_calender=(ImageView)findViewById(R.id.ic_calender);
        Quality=(EditText)findViewById(R.id.Quality);
        remarks=(EditText)findViewById(R.id.remarks);

        String getempname=operatorCode.getemp_first_name()+" "+operatorCode.getemp_last_name();
        empname.setText("Hello "+getempname);
        selectdate.setText("");

        getempID=operatorCode.getemp_id();
        getComplaintsList(getempID);

        Intent dataIntent1 = new Intent(IndentRaisingActivity.this, DataLoader.class);
        Messenger dataMessenger1 = new Messenger(iHandler);
        dataIntent1.putExtra("MESSENGER", dataMessenger1);
        dataIntent1.putExtra("type", DataLoader.DataType.Inventory_Items.ordinal());
        startService(dataIntent1);

        inventory_complaint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indent_reason=complaintsDataList.get(position).getcomplaint_id();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        inventory_item.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inv_id=inventoryList.get(position).getinv_id();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getComplaintsList(String getempID) {
        Intent dataIntent = new Intent(IndentRaisingActivity.this, DataLoader.class);
        Messenger dataMessenger = new Messenger(complaintHandler);
        dataIntent.putExtra("MESSENGER", dataMessenger);
        dataIntent.putExtra("type", DataLoader.DataType.COMPLAINTS_LIST.ordinal());
        dataIntent.putExtra("jsonObject", getempID);
        startService(dataIntent);
    }
    public void openDateCalendar(View view){
         int mYear, mMonth, mDay, mHour, mMinute;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        ic_calender.setVisibility(View.GONE);
                        selectdate.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    private Handler complaintHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle   bundle = msg.getData();
            String response = bundle.getString("data");
            try{
                JSONObject responseObj = new JSONObject(response);
                String message = responseObj.getString("message");
                if (message.equalsIgnoreCase("success")) {
                    Iterator<String> keys = responseObj.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            ComplaintData complaintData = new Gson().fromJson(responseObj.getJSONObject(key).toString(),new TypeToken<ComplaintData>() {}.getType());
                            complaintsDataList.add(complaintData);
                        }

                        SpinnerComplaintAdapter adapter = new SpinnerComplaintAdapter(IndentRaisingActivity.this, complaintsDataList);
                        inventory_complaint.setAdapter(adapter);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };

    private Handler iHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle   bundle = msg.getData();
            String response = bundle.getString("data");
            try{
                JSONObject responseObj = new JSONObject(response);
                String message = responseObj.getString("message");
                if (message.equalsIgnoreCase("success")) {
                    inventoryList=new ArrayList<>();
                    Iterator<String> keys = responseObj.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            InventoryItemsData inventoryItemsData = new Gson().fromJson(responseObj.getJSONObject(key).toString(),new TypeToken<InventoryItemsData>() {}.getType());
                            inventoryList.add(inventoryItemsData);
                        }
                    }
                    SpinnerInventoryAdapter adapter = new SpinnerInventoryAdapter(IndentRaisingActivity.this, inventoryList);
                    inventory_item.setAdapter(adapter);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    public void submit(View view){
        required_date=selectdate.getText().toString();
        required_qty=Quality.getText().toString().trim();

        Intent dataIntent = new Intent(IndentRaisingActivity.this, DataLoader.class);
        Messenger dataMessenger = new Messenger(INDENTHandler);
        dataIntent.putExtra("MESSENGER", dataMessenger);
        dataIntent.putExtra("type", DataLoader.DataType.INDENT_RASING.ordinal());
        dataIntent.putExtra("getempID", getempID);
        dataIntent.putExtra("inv_id", inv_id);
        dataIntent.putExtra("required_qty", required_qty);
        dataIntent.putExtra("indent_reason", indent_reason);
        dataIntent.putExtra("required_date", required_date);
        startService(dataIntent);
    }

    private Handler INDENTHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle   bundle = msg.getData();
            String response = bundle.getString("data");
            try{
                JSONObject responseObj = new JSONObject(response);
                String message = responseObj.getString("message");
                String invoice_id=responseObj.getString("invoice_id");
                if (message.equalsIgnoreCase("success")) {
                    Iterator<String> keys = responseObj.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            Intent intent=new Intent(IndentRaisingActivity.this,IndentSuccessActivity.class);
                            intent.putExtra("invoice_id",invoice_id);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
}
