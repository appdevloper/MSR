package com.digitalrupay.msrc.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalrupay.msrc.R;
import com.digitalrupay.msrc.activitys.admin.AdminHomeActivity;
import com.digitalrupay.msrc.activitys.emp.EMPHomeActivity;
import com.digitalrupay.msrc.activitys.stockist.StockistHomeActivity;
import com.digitalrupay.msrc.backendServices.DataLoader;
import com.digitalrupay.msrc.dataModel.OperatorCode;
import com.digitalrupay.msrc.dataModel.OperatorLoginData;
import com.digitalrupay.msrc.saveAppData.SaveAppData;
import com.digitalrupay.msrc.utils.ImageLoader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class EMPLoginActivity extends AppCompatActivity {
    TextView busiName;
    EditText Operator_username,Operator_password;
    ImageView c_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emplogin);
        OperatorCode operatorCode=null;
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorData();
        String bName=operatorCode.getop_busiName();
        String uri=operatorCode.getop_logo();
        busiName=(TextView)findViewById(R.id.busiName);
        Operator_username=(EditText)findViewById(R.id.Operator_username);
        Operator_password=(EditText)findViewById(R.id.Operator_password);
        c_logo=(ImageView)findViewById(R.id.c_logo);
        busiName.setText(bName);
        ImageLoader imgLoader = new ImageLoader(getApplicationContext());
        int loader = R.drawable.splash_logo;
        imgLoader.DisplayImage(uri, loader, c_logo);

    }
    public void login(View view){
        String getOperator_username=Operator_username.getText().toString().trim();
        String getOperator_password=Operator_password.getText().toString().trim();
        Intent dataIntent = new Intent(EMPLoginActivity.this, DataLoader.class);
        Messenger dataMessenger = new Messenger(mHandler);
        dataIntent.putExtra("MESSENGER", dataMessenger);
        dataIntent.putExtra("type", DataLoader.DataType.EMPLOYEE_LOGIN.ordinal());
        dataIntent.putExtra("username", getOperator_username);
        dataIntent.putExtra("password", getOperator_password);
        startService(dataIntent);
    }
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle   bundle = msg.getData();
            String response = bundle.getString("data");
            try{
                JSONObject responseObj = new JSONObject(response);
                String message = responseObj.getString("message");
                String text = responseObj.getString("text");
                if (message.equalsIgnoreCase("success")) {
                    Iterator<String> keys = responseObj.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            OperatorLoginData operatorLoginData = new Gson().fromJson(responseObj.getJSONObject(key).toString(),new TypeToken<OperatorLoginData>() {}.getType());
                            SaveAppData.getSessionDataInstance().saveOperatorLoginData(operatorLoginData);

                            OperatorLoginData operatorCode=null;
                            operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();

                            String type=operatorCode.getuser_type();

                            if(type.equalsIgnoreCase("4")) {
                                Intent intent = new Intent(EMPLoginActivity.this, EMPHomeActivity.class);
                                startActivity(intent);
                                EMPLoginActivity.this.finish();
                            }else if(type.equalsIgnoreCase("1")){
                                Intent intent = new Intent(EMPLoginActivity.this, AdminHomeActivity.class);
                                startActivity(intent);
                                EMPLoginActivity.this.finish();
                            }else if(type.equalsIgnoreCase("2")){
                                Intent intent = new Intent(EMPLoginActivity.this, StockistHomeActivity.class);
                                startActivity(intent);
                                EMPLoginActivity.this.finish();
                            }
                        }
                    }
                }else{
                    Operator_username.setText("");
                    Operator_password.setText("");
                    Toast.makeText(EMPLoginActivity.this,text,Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };


}
