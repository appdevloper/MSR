package com.digitalrupay.msrc.activitys.stockist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.digitalrupay.msrc.R;
import com.digitalrupay.msrc.activitys.BaseActivity;
import com.digitalrupay.msrc.activitys.SearchOperatorCode;
import com.digitalrupay.msrc.activitys.admin.AdminHomeActivity;
import com.digitalrupay.msrc.activitys.admin.EditAdminInventoryActivity;
import com.digitalrupay.msrc.activitys.emp.EMPHomeActivity;
import com.digitalrupay.msrc.adapter.AdminListAdapter;
import com.digitalrupay.msrc.adapter.StockistInventoryAdapter;
import com.digitalrupay.msrc.backendServices.DataLoader;
import com.digitalrupay.msrc.dataModel.AdmIninventoryData;
import com.digitalrupay.msrc.dataModel.OperatorLoginData;
import com.digitalrupay.msrc.dataModel.StockistInventoryData;
import com.digitalrupay.msrc.saveAppData.SaveAppData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class StockistHomeActivity extends AppCompatActivity {
    TextView empname;
    ListView admin_inventory_list;
    ArrayList<StockistInventoryData> stockistinventoryDataList;
    StockistInventoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockist_home);

        OperatorLoginData operatorCode=null;
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
        empname=(TextView)findViewById(R.id.empname);
        admin_inventory_list=(ListView)findViewById(R.id.stockist_inventory_list);
        String getempID=operatorCode.getemp_id();
        String getempname=operatorCode.getemp_first_name()+" "+operatorCode.getemp_last_name();
        empname.setText("Hello "+getempname);

        admin_inventory_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StockistInventoryData stockinventoryData=stockistinventoryDataList.get(position);
                Intent updateintent=new Intent(StockistHomeActivity.this,EditStockInventoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("stockinventoryData", stockinventoryData);
                updateintent.putExtras(bundle);
                startActivityForResult(updateintent,200);
            }
        });
        Intent dataIntent = new Intent(StockistHomeActivity.this, DataLoader.class);
        Messenger dataMessenger = new Messenger(EHandler);
        dataIntent.putExtra("MESSENGER", dataMessenger);
        dataIntent.putExtra("type", DataLoader.DataType.stockist_inventory.ordinal());
        dataIntent.putExtra("jsonObject", getempID);
        startService(dataIntent);
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
                    stockistinventoryDataList=new ArrayList<>();
                    Iterator<String> keys = responseObj.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            StockistInventoryData admIninventoryData = new Gson().fromJson(responseObj.getJSONObject(key).toString(),new TypeToken<StockistInventoryData>() {}.getType());
                            stockistinventoryDataList.add(admIninventoryData);
                        }
                        adapter=new StockistInventoryAdapter(StockistHomeActivity.this,stockistinventoryDataList);
                        admin_inventory_list.setAdapter(adapter);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };
}
