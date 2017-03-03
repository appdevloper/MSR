package com.digitalrupay.msrc.activitys.emp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.digitalrupay.msrc.R;
import com.digitalrupay.msrc.activitys.BaseActivity;
import com.digitalrupay.msrc.adapter.ComplaintAdapter;
import com.digitalrupay.msrc.backendServices.DataLoader;
import com.digitalrupay.msrc.dataModel.ComplaintData;
import com.digitalrupay.msrc.dataModel.OperatorLoginData;
import com.digitalrupay.msrc.saveAppData.SaveAppData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ComplaintsActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    TextView empname;
    ListView complaintslist;
    ArrayList<ComplaintData> complaintsDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        OperatorLoginData operatorCode=null;
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();

        empname=(TextView)findViewById(R.id.in_empname);
        complaintslist=(ListView)findViewById(R.id.complaintlist);

        String getempID=operatorCode.getemp_id();
        getComplaintsList(getempID);

        String getempname=operatorCode.getemp_first_name()+" "+operatorCode.getemp_last_name();
        empname.setText("Hello "+getempname);

        complaintslist.setOnItemClickListener(this);
    }

    private void getComplaintsList(String getempID) {
        Intent dataIntent = new Intent(ComplaintsActivity.this, DataLoader.class);
        Messenger dataMessenger = new Messenger(COMPLAINTS_LISTHandler);
        dataIntent.putExtra("MESSENGER", dataMessenger);
        dataIntent.putExtra("type", DataLoader.DataType.COMPLAINTS_LIST.ordinal());
        dataIntent.putExtra("jsonObject", getempID);
        startService(dataIntent);
    }

    private Handler COMPLAINTS_LISTHandler=new Handler(){
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
                        ComplaintAdapter adapter=new ComplaintAdapter(ComplaintsActivity.this,complaintsDataList);
                        complaintslist.setAdapter(adapter);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ComplaintData complaintData=complaintsDataList.get(position);
        Intent updateintent=new Intent(ComplaintsActivity.this,UpdateComplaintActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("complaintData", complaintData);
        updateintent.putExtras(bundle);
        startActivity(updateintent);
    }
}
