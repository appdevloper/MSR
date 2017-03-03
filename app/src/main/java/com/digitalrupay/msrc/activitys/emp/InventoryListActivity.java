package com.digitalrupay.msrc.activitys.emp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.widget.ListView;
import android.widget.TextView;

import com.digitalrupay.msrc.R;
import com.digitalrupay.msrc.activitys.BaseActivity;
import com.digitalrupay.msrc.adapter.InventoryListAdapter;
import com.digitalrupay.msrc.backendServices.DataLoader;
import com.digitalrupay.msrc.dataModel.InventoryData;
import com.digitalrupay.msrc.dataModel.OperatorLoginData;
import com.digitalrupay.msrc.saveAppData.SaveAppData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class InventoryListActivity extends BaseActivity {
    TextView empname;
    ListView inventorylist;
    ArrayList<InventoryData> inventoryDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);
        OperatorLoginData operatorCode=null;
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
        empname=(TextView)findViewById(R.id.in_empname);
        inventorylist=(ListView)findViewById(R.id.inventorylist);
        String getempname=operatorCode.getemp_first_name()+" "+operatorCode.getemp_last_name();
        empname.setText("Hello "+getempname);
        String getempID=operatorCode.getemp_id();
        getInventoryList(getempID);
    }
    private void getInventoryList(String getempID) {
        Intent dataIntent = new Intent(InventoryListActivity.this, DataLoader.class);
        Messenger dataMessenger = new Messenger(iHandler);
        dataIntent.putExtra("MESSENGER", dataMessenger);
        dataIntent.putExtra("type", DataLoader.DataType.EMPLOYEE_INVENTORY.ordinal());
        dataIntent.putExtra("jsonObject", getempID);
        startService(dataIntent);
    }
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

                    Iterator<String> keys = responseObj.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            InventoryData inventoryData = new Gson().fromJson(responseObj.getJSONObject(key).toString(),new TypeToken<InventoryData>() {}.getType());
                            inventoryDataList.add(inventoryData);
                        }
                        InventoryListAdapter adapter=new InventoryListAdapter(InventoryListActivity.this,inventoryDataList);
                        inventorylist.setAdapter(adapter);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };
}
