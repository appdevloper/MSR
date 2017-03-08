package com.digitalrupay.msrc.activitys.admin;

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
import com.digitalrupay.msrc.activitys.EMPLoginActivity;
import com.digitalrupay.msrc.activitys.SearchOperatorCode;
import com.digitalrupay.msrc.activitys.emp.ComplaintsActivity;
import com.digitalrupay.msrc.activitys.emp.EMPHomeActivity;
import com.digitalrupay.msrc.activitys.emp.InventoryActivity;
import com.digitalrupay.msrc.activitys.stockist.StockistHomeActivity;
import com.digitalrupay.msrc.adapter.AdminListAdapter;
import com.digitalrupay.msrc.adapter.ComplaintAdapter;
import com.digitalrupay.msrc.backendServices.DataLoader;
import com.digitalrupay.msrc.dataModel.AdmIninventoryData;
import com.digitalrupay.msrc.dataModel.ComplaintData;
import com.digitalrupay.msrc.dataModel.OperatorLoginData;
import com.digitalrupay.msrc.saveAppData.SaveAppData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity {
    TextView empname;
    ListView admin_inventory_list;
    ArrayList<AdmIninventoryData>  admininventoryDataList;
    AdminListAdapter adapter;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_home);
            OperatorLoginData operatorCode=null;
            operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
            empname=(TextView)findViewById(R.id.empname);
            admin_inventory_list=(ListView)findViewById(R.id.admin_inventory_list);
            String getempID=operatorCode.getemp_id();
            String getempname=operatorCode.getemp_first_name()+" "+operatorCode.getemp_last_name();
            empname.setText("Hello "+getempname);
            Intent dataIntent = new Intent(AdminHomeActivity.this, DataLoader.class);
            Messenger dataMessenger = new Messenger(EHandler);
            dataIntent.putExtra("MESSENGER", dataMessenger);
            dataIntent.putExtra("type", DataLoader.DataType.admin_inventory.ordinal());
            dataIntent.putExtra("jsonObject", getempID);
            startService(dataIntent);

            admin_inventory_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AdmIninventoryData admIninventoryData=admininventoryDataList.get(position);
                    Intent updateintent=new Intent(AdminHomeActivity.this,EditAdminInventoryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AdmIninventoryData", admIninventoryData);
                    updateintent.putExtras(bundle);
                    startActivityForResult(updateintent,200);
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
                    admininventoryDataList=new ArrayList<>();
                    Iterator<String> keys = responseObj.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            AdmIninventoryData admIninventoryData = new Gson().fromJson(responseObj.getJSONObject(key).toString(),new TypeToken<AdmIninventoryData>() {}.getType());
                            admininventoryDataList.add(admIninventoryData);
                        }
                        adapter=new AdminListAdapter(AdminHomeActivity.this,admininventoryDataList);
                        admin_inventory_list.setAdapter(adapter);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
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
