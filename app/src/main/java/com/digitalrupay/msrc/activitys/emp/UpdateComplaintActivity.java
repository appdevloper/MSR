package com.digitalrupay.msrc.activitys.emp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.*;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalrupay.msrc.R;
import com.digitalrupay.msrc.activitys.BaseActivity;
import com.digitalrupay.msrc.adapter.ComplaintInventoryListAdapter;
import com.digitalrupay.msrc.adapter.SpinnerClosedCompAdapter;
import com.digitalrupay.msrc.backendServices.DataLoader;
import com.digitalrupay.msrc.backendServices.GPSTracker;
import com.digitalrupay.msrc.dataModel.CategoryListData;
import com.digitalrupay.msrc.dataModel.ClosedcompData;
import com.digitalrupay.msrc.dataModel.ComplaintData;
import com.digitalrupay.msrc.dataModel.InventoryData;
import com.digitalrupay.msrc.dataModel.OperatorLoginData;
import com.digitalrupay.msrc.dataModel.UpdateAdmininventoryData;
import com.digitalrupay.msrc.saveAppData.SaveAppData;
import com.digitalrupay.msrc.utils.ImageUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateComplaintActivity extends BaseActivity {
    ComplaintData complaintData;
    TextView empname, complaint_stats;
    ArrayList<CategoryListData> categoryList;
    LinearLayout Add_complaints_definition, section_inventory,sub1_section_inventory,sub2_section_inventory;
    boolean isOpen;
    EditText Add_complaints, used_qty;
    Spinner sp_pending_complaints, select_inventory,select_inventory1,sp_closed_complaints,select_inventory2;
    String[] pending_complaints;
    ArrayList<InventoryData> inventoryDataList = new ArrayList<>();
    CheckBox check_used_inventory;
    RelativeLayout sub_section_inventory;
    GPSTracker gps;
    String complaint_id,comp_status,getempID,remarks,closed_img_name,gio_loc,ba1,picturePath="",getcomp_cat, cos_data,getclosedcompID;
    Uri selectedImage,fileUri;
    Bitmap photo;
    public static String URL = "http://devtest.digitalrupay.com/webservices/uploads/upload.php";
    ImageView imageView,Imageprev1,Imageprev2;
    ArrayList<ClosedcompData> listclosedcompData=new ArrayList<>();
    private Timer mTimer1;
    private TimerTask mTt1;
    private Handler mTimerHandler = new Handler();
    FloatingActionButton FAB;
    ScrollView scroll_move;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_complaint);
        isOpen = false;
        complaintData = (ComplaintData) getIntent().getExtras().getSerializable("complaintData");

        OperatorLoginData operatorCode = null;
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();

        getempID = operatorCode.getemp_id();

        Intent dataIntent = new Intent(UpdateComplaintActivity.this, DataLoader.class);
        Messenger dataMessenger = new Messenger(COMPLAINTS_CATEGORYHandler);
        dataIntent.putExtra("MESSENGER", dataMessenger);
        dataIntent.putExtra("type", DataLoader.DataType.COMPLAINTS_CATEGORY_LIST.ordinal());
        startService(dataIntent);


        empname=(TextView)findViewById(R.id.in_empname);
        complaint_stats=(TextView)findViewById(R.id.complaint_stats);
        Add_complaints_definition=(LinearLayout) findViewById(R.id.Add_complaints_definition);
        Add_complaints=(EditText)findViewById(R.id.Add_complaints);
        sp_pending_complaints=(Spinner)findViewById(R.id.sp_pending_complaints);
        sp_closed_complaints=(Spinner)findViewById(R.id.sp_closed_complaints);
        section_inventory=(LinearLayout)findViewById(R.id.section_inventory);
        select_inventory=(Spinner) findViewById(R.id.select_inventory);
        select_inventory1=(Spinner)findViewById(R.id.select_inventory1);
        select_inventory2=(Spinner)findViewById(R.id.select_inventory2);
        used_qty=(EditText)findViewById(R.id.used_qty);
        check_used_inventory=(CheckBox)findViewById(R.id.check_used_inventory);
        sub_section_inventory=(RelativeLayout)findViewById(R.id.sub_section_inventory);
        imageView = (ImageView) findViewById(R.id.Imageprev);
        Imageprev1=(ImageView)findViewById(R.id.Imageprev1);
        Imageprev2=(ImageView)findViewById(R.id.Imageprev2);
        scroll_move=(ScrollView)findViewById(R.id.scroll_move);
        sub1_section_inventory=(LinearLayout)findViewById(R.id.sub1_section_inventory);
        sub2_section_inventory=(LinearLayout)findViewById(R.id.sub2_section_inventory);
        FAB = (FloatingActionButton) findViewById(R.id.fab);
        FAB.setVisibility(View.GONE);
        count=0;
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scroll_move.fullScroll(ScrollView.FOCUS_DOWN);
                count=count+1;
                if(count==1){
                    sub1_section_inventory.setVisibility(View.VISIBLE);
                }else if(count==2){
                    sub2_section_inventory.setVisibility(View.VISIBLE);
                }else {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.fab), "Only Three Edit Options", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });
        getInventoryList();
        complaint_id=complaintData.getcomplaint_id();
        cos_data=complaintData.getfirst_name()+" ("+complaintData.getcustom_customer_no()+") "+complaintData.getmobile_no()+", "+complaintData.getcity();
        getcomp_cat=complaintData.getcomp_cat();
        empname.setText(cos_data);

        pending_complaints=getResources().getStringArray(R.array.PendingComplaintsList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_complaint, pending_complaints);
        sp_pending_complaints.setAdapter(adapter);
        String getcomp_status=complaintData.getcomp_status();
        int position=Integer.parseInt(getcomp_status);
        if(position==0){
            sp_pending_complaints.setSelection(0);
        }else if(position==1){
            sp_pending_complaints.setSelection(1);
        }else if(position==2){
            sp_pending_complaints.setSelection(2);
        }

        gps = new GPSTracker(UpdateComplaintActivity.this);
        if(!gps.canGetLocation()){
            gps.showSettingsAlert();
        }

        startTimer();
        sp_pending_complaints.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String getcomplaint=pending_complaints[position];
                if(getcomplaint.equalsIgnoreCase("Pending")){
                    comp_status="0";
                    section_inventory.setVisibility(View.GONE);
                }else if(getcomplaint.equalsIgnoreCase("Processing")){
                    comp_status="1";
                    section_inventory.setVisibility(View.GONE);
                }else if(getcomplaint.equalsIgnoreCase("Completed")){
                    comp_status="2";
                    section_inventory.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        check_used_inventory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sub_section_inventory.setVisibility(View.VISIBLE);
                }else {
                    sub_section_inventory.setVisibility(View.GONE);
                }
            }
        });
        getCloseComplaintComt();
        sp_closed_complaints.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getclosedcompID=listclosedcompData.get(position).getclosed_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                getclosedcompID="";
            }
        });
    }

    private void getCloseComplaintComt() {
        Intent dataIntent1 = new Intent(UpdateComplaintActivity.this, DataLoader.class);
        Messenger dataMessenger1 = new Messenger(CLOSEDCOMPHandler);
        dataIntent1.putExtra("MESSENGER", dataMessenger1);
        dataIntent1.putExtra("type", DataLoader.DataType.CLOSEDCOMP.ordinal());
        startService(dataIntent1);
    }

    private Handler CLOSEDCOMPHandler=new Handler(){
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
                            ClosedcompData closedcompData = new Gson().fromJson(responseObj.getJSONObject(key).toString(),new TypeToken<ClosedcompData>() {}.getType());
                            listclosedcompData.add(closedcompData);
                        }
                        SpinnerClosedCompAdapter adapter=new SpinnerClosedCompAdapter(UpdateComplaintActivity.this,listclosedcompData);
                        sp_closed_complaints.setAdapter(adapter);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };
    private void getInventoryList() {
        Intent dataIntent1 = new Intent(UpdateComplaintActivity.this, DataLoader.class);
        Messenger dataMessenger1 = new Messenger(InventoryHandler);
        dataIntent1.putExtra("MESSENGER", dataMessenger1);
        dataIntent1.putExtra("type", DataLoader.DataType.EMPLOYEE_INVENTORY.ordinal());
        dataIntent1.putExtra("jsonObject", getempID);
        startService(dataIntent1);
    }
    private Handler InventoryHandler=new Handler(){
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
                        ComplaintInventoryListAdapter adapter=new ComplaintInventoryListAdapter(UpdateComplaintActivity.this,inventoryDataList);
                        select_inventory.setAdapter(adapter);
                        select_inventory1.setAdapter(adapter);
                        select_inventory2.setAdapter(adapter);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };
    private Handler COMPLAINTS_CATEGORYHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle   bundle = msg.getData();
            String response = bundle.getString("data");
            try{
                JSONObject responseObj = new JSONObject(response);
                String message = responseObj.getString("message");
                if (message.equalsIgnoreCase("success")) {
                    categoryList=new ArrayList<>();
                    Iterator<String> keys = responseObj.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            CategoryListData categoryListData = new Gson().fromJson(responseObj.getJSONObject(key).toString(),new TypeToken<CategoryListData>() {}.getType());
                            categoryList.add(categoryListData);
                        }
                    }
                    for(int i=0; i<categoryList.size();i++){
                        String comp_id=categoryList.get(i).getid();
                        if(comp_id.equalsIgnoreCase(getcomp_cat)){
                            String comp_messege=categoryList.get(i).getcategory();
                            complaint_stats.setText(comp_messege);
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    public void submit(View view){
        remarks=Add_complaints.getText().toString().replace(" ","%20");
        double latitude=0.0,longitude=0.0;
        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude  = gps.getLongitude();
        }else{
            gps.showSettingsAlert();
        }
        gio_loc=latitude+","+longitude;
        if(picturePath.length()==0) {
                Intent dataIntent = new Intent(UpdateComplaintActivity.this, DataLoader.class);
                Messenger dataMessenger = new Messenger(complaintsHandler);
                dataIntent.putExtra("MESSENGER", dataMessenger);
                dataIntent.putExtra("type", DataLoader.DataType.COMPLAINT_EDIT.ordinal());
                dataIntent.putExtra("complaint_id", complaint_id);
                dataIntent.putExtra("comp_status", comp_status);
                dataIntent.putExtra("emp_id", getempID);
                dataIntent.putExtra("remarks", remarks);
                dataIntent.putExtra("closed_img", "");
                dataIntent.putExtra("getclosedcompID", getclosedcompID);
                dataIntent.putExtra("gio_loc", gio_loc);
                startService(dataIntent);

        }else {
            upload();
        }
    }

    private Handler complaintsHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle   bundle = msg.getData();
            String response = bundle.getString("data");
            try{
                JSONObject responseObj = new JSONObject(response);
                String message = responseObj.getString("message");
                if (message.equalsIgnoreCase("success")) {
                    final Dialog dialog=new Dialog(UpdateComplaintActivity.this);
                    dialog.setContentView(R.layout.dialogcomplaint);
                    dialog.setCancelable(false);
                    Button button=(Button) dialog.findViewById(R.id.Redirecttodashboard);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
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
    public void captureImage(View view){
        clickpic();
    }
    private void upload() {
        Bitmap bitmap = ImageUtils.getInstant().getCompressedBitmap(picturePath);
        int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        scaled.compress(Bitmap.CompressFormat.JPEG, 50, bao);
        byte[] ba = bao.toByteArray();
        ba1 = Base64.encodeToString(ba, Base64.NO_WRAP);

        new uploadToServer(UpdateComplaintActivity.this).execute();
    }
    public void captureImage1(View view){
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, 101);
        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }
    public void captureImage2(View view){
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, 102);
        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }
    private void clickpic() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, 100);
        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            selectedImage = data.getData();
            photo = (Bitmap) data.getExtras().get("data");
            double latitude=0.0,longitude=0.0;
            if(gps.canGetLocation()){
                latitude = gps.getLatitude();
                longitude  = gps.getLongitude();
            }else{
                gps.showSettingsAlert();
            }
            gio_loc=latitude+","+longitude;
            int w = photo.getWidth();
            int h = photo.getHeight();

            Bitmap result = Bitmap.createBitmap(w, h, photo.getConfig());

            Canvas canvas = new Canvas(result);
            canvas.drawBitmap(photo, 0, 0, null);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLUE);
            paint.setTextSize(12);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawText(gio_loc, 15, 200, paint);

            File outputFile = new File(Environment.getExternalStorageDirectory(), getempID+"_"+System.currentTimeMillis() + ".jpg");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                result.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            picturePath=outputFile.getPath();
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(result);
            FAB.setVisibility(View.VISIBLE);
        }else if (requestCode == 101 && resultCode == RESULT_OK) {
                selectedImage = data.getData();
                photo = (Bitmap) data.getExtras().get("data");
                double latitude=0.0,longitude=0.0;
                if(gps.canGetLocation()){
                    latitude = gps.getLatitude();
                    longitude  = gps.getLongitude();
                }else{
                    gps.showSettingsAlert();
                }
                gio_loc=latitude+","+longitude;
                int w = photo.getWidth();
                int h = photo.getHeight();

                Bitmap result = Bitmap.createBitmap(w, h, photo.getConfig());

                Canvas canvas = new Canvas(result);
                canvas.drawBitmap(photo, 0, 0, null);

                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.BLUE);
                paint.setTextSize(12);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawText(gio_loc, 15, 200, paint);

                File outputFile = new File(Environment.getExternalStorageDirectory(), getempID+"_"+System.currentTimeMillis() +count+ ".jpg");
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                    result.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                picturePath=outputFile.getPath();
            Imageprev1.setVisibility(View.VISIBLE);
            Imageprev1.setImageBitmap(result);
            }
        else if (requestCode == 102 && resultCode == RESULT_OK) {
            selectedImage = data.getData();
            photo = (Bitmap) data.getExtras().get("data");
            double latitude=0.0,longitude=0.0;
            if(gps.canGetLocation()){
                latitude = gps.getLatitude();
                longitude  = gps.getLongitude();
            }else{
                gps.showSettingsAlert();
            }
            gio_loc=latitude+","+longitude;
            int w = photo.getWidth();
            int h = photo.getHeight();

            Bitmap result = Bitmap.createBitmap(w, h, photo.getConfig());

            Canvas canvas = new Canvas(result);
            canvas.drawBitmap(photo, 0, 0, null);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLUE);
            paint.setTextSize(12);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawText(gio_loc, 15, 200, paint);

            File outputFile = new File(Environment.getExternalStorageDirectory(), getempID+"_"+System.currentTimeMillis() +count+ ".jpg");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                result.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            picturePath=outputFile.getPath();
            Imageprev2.setVisibility(View.VISIBLE);
            Imageprev2.setImageBitmap(result);
        }
    }
    public class uploadToServer extends AsyncTask<Void, Void, String> {
        Context context;
        private ProgressDialog pd = new ProgressDialog(UpdateComplaintActivity.this);

        public uploadToServer(Context con) {
            this.context=con;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Wait image uploading!");
            pd.setCancelable(false);
            pd.show();
        }
        @Override
        protected String doInBackground(Void... params) {
            closed_img_name=getempID+"_"+System.currentTimeMillis() + ".png";
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("base64", ba1));
            nameValuePairs.add(new BasicNameValuePair("ImageName", closed_img_name));
            try {

                Intent dataIntent = new Intent(context, DataLoader.class);
                Messenger dataMessenger = new Messenger(complaintsHandler);
                dataIntent.putExtra("MESSENGER", dataMessenger);
                dataIntent.putExtra("type", DataLoader.DataType.COMPLAINT_EDIT.ordinal());
                dataIntent.putExtra("complaint_id", complaint_id);
                dataIntent.putExtra("comp_status", comp_status);
                dataIntent.putExtra("emp_id", getempID);
                dataIntent.putExtra("remarks", remarks);
                dataIntent.putExtra("closed_img", closed_img_name);
                dataIntent.putExtra("getclosedcompID",getclosedcompID);
                dataIntent.putExtra("gio_loc", gio_loc);
                startService(dataIntent);

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                String st = EntityUtils.toString(response.getEntity());
            } catch (Exception e) {
                e.printStackTrace();
                resetData();
                Toast.makeText(UpdateComplaintActivity.this,"Upload Fail try agen",Toast.LENGTH_LONG).show();
            }
            return "Success";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pd.hide();
            pd.dismiss();
        }
    }
    private void resetData() {
        imageView.setImageResource(0);
        imageView.setVisibility(View.GONE);
        Add_complaints.setText("");
    }
    private void stopTimer(){
        if(mTimer1 != null){
            mTimer1.cancel();
            mTimer1.purge();
        }
    }
    private void startTimer(){
        mTimer1 = new Timer();
        mTt1 = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run(){
                        //TODO
                        double latitude=0.0,longitude=0.0;
                        if(gps.canGetLocation()){
                            latitude = gps.getLatitude();
                            longitude  = gps.getLongitude();
                            Log.e("GetLocation","latitude :- "+latitude+"\n longitude :- "+longitude);
                            Intent dataIntent = new Intent(UpdateComplaintActivity.this, DataLoader.class);
                            Messenger dataMessenger = new Messenger(hEMPGPSLOC);
                            dataIntent.putExtra("MESSENGER", dataMessenger);
                            dataIntent.putExtra("type", DataLoader.DataType.EMPGPSLOC.ordinal());
                            dataIntent.putExtra("gps_lang",""+longitude);
                            dataIntent.putExtra("gps_lat",""+latitude);
                            dataIntent.putExtra("emp_id",getempID);
                            startService(dataIntent);
                        }else{
                            gps.showSettingsAlert();
                        }
                    }
                });
            }
        };
        mTimer1.schedule(mTt1, 1, 5000);
    }
    private Handler hEMPGPSLOC=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle   bundle = msg.getData();
            String response = bundle.getString("data");
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        stopTimer();
    }
}