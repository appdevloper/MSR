package com.digitalrupay.msrc.backendServices;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.digitalrupay.msrc.R;
import com.digitalrupay.msrc.dataModel.OperatorCode;
import com.digitalrupay.msrc.saveAppData.SaveAppData;

public class DataLoader extends IntentService {
	private static final String LOG_TAG = "Data Loader";
	public enum DataType {
		OPERATOR_CODE,EMPLOYEE_LOGIN,EMPLOYEE_INVENTORY,COMPLAINTS_LIST,COMPLAINTS_CATEGORY_LIST,COMPLAINT_EDIT,INDENT_RASING,Inventory_Items,
		admin_inventory,admin_inventory_edit,admin_inv_update,stockist_inventory,stockist_inventory_edit,stockist_inv_update,EMPGPSLOC,CLOSEDCOMP}

	public DataLoader() {
		super("Data Loader");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		DataType dataType = DataType.values()[intent.getIntExtra("type", 0)];

		String Object = intent.getStringExtra("jsonObject");
		String username = intent.getStringExtra("username");
		String password = intent.getStringExtra("password");

		String complaint_id = intent.getStringExtra("complaint_id");
		String comp_status = intent.getStringExtra("comp_status");
		String emp_id = intent.getStringExtra("emp_id");
		String remarks = intent.getStringExtra("remarks");
		String closed_img = intent.getStringExtra("closed_img");
		String gio_loc = intent.getStringExtra("gio_loc");
		String getclosedcompID=intent.getStringExtra("getclosedcompID");

		String getempID = intent.getStringExtra("getempID");
		String inv_id = intent.getStringExtra("inv_id");
		String required_qty = intent.getStringExtra("required_qty");
		String indent_reason = intent.getStringExtra("indent_reason");
		String required_date = intent.getStringExtra("required_date");

		String empid=intent.getStringExtra("empID");
		String indentid=intent.getStringExtra("indentid");
		String status=intent.getStringExtra("status");
		String approved_qty=intent.getStringExtra("approved_qty");
		String shp_charges=intent.getStringExtra("shp_charges");
		String inward_remarks=intent.getStringExtra("inward_remarks");
		String shp_date=intent.getStringExtra("shp_date");

		String gps_lang=intent.getStringExtra("gps_lang");
		String gps_lat=intent.getStringExtra("gps_lat");

		String used_qty=intent.getStringExtra("used_qty");
		String inventoryid=intent.getStringExtra("inventoryID");

		APICaller caller = new APICaller();
		String data = new String();
		String uri,response;
		String mainURL="";
		if (SaveAppData.getSessionDataInstance().getOperatorData() != null || SaveAppData.getSessionDataInstance().getOperatorLoginData() != null ) {
			OperatorCode operatorCode = null;
			operatorCode = SaveAppData.getSessionDataInstance().getOperatorData();
			mainURL = operatorCode.getop_url();
		}
		switch (dataType) {
			case OPERATOR_CODE:
				uri=getResources().getString(R.string.OPERATOR_CODE)+Object;
				caller=new APICaller();
				response = caller.GetDataFromUrl(uri);
				data=response;
				break;
			case EMPLOYEE_LOGIN:
				uri=mainURL+"emp_login.php?"+"email="+username+"&pwd="+password;
				caller=new APICaller();
				response = caller.GetDataFromUrl(uri);
				data=response;
				break;
			case EMPLOYEE_INVENTORY:
				uri=mainURL+"emp_inventory.php?emp_id="+Object;
				caller=new APICaller();
				response = caller.GetDataFromUrl(uri);
				data=response;
				break;
			case COMPLAINTS_LIST:
				uri=mainURL+"complaints_details.php?emp_id="+Object;
				caller=new APICaller();
				response = caller.GetDataFromUrl(uri);
				data=response;
				Log.e("URI",uri);
				break;
			case COMPLAINTS_CATEGORY_LIST:
				uri=mainURL+"get_comp_cat.php";
				caller=new APICaller();
				response = caller.GetDataFromUrl(uri);
				data=response;
				break;
			case COMPLAINT_EDIT:
				uri=mainURL+"complaints_edit.php?complaint_id="+complaint_id+"&comp_status="+comp_status+"&emp_id="+emp_id+"&remarks="+remarks+"&comp_closed_cat="+getclosedcompID+"&closed_img="+closed_img+"&gio_loc="+gio_loc+"&inventoryid="+inventoryid+"&used_qty="+used_qty+"";
				Log.e("uri",uri);
				caller=new APICaller();
				response = caller.GetDataFromUrl(uri);
				data=response;
				break;
			case INDENT_RASING:
				uri=mainURL+"indent_raising.php?emp_id="+getempID+"&inv_id="+inv_id+"&required_qty="+required_qty+"&indent_reason="+indent_reason+"&required_date="+required_date+"";
				caller=new APICaller();
				response = caller.GetDataFromUrl(uri);
				data=response;
				break;
			case Inventory_Items:
				uri=mainURL+"inventory_items.php";
				caller=new APICaller();
				response = caller.GetDataFromUrl(uri);
				data=response;
				break;
			case admin_inventory:
				uri=mainURL+"admin_inventory.php?emp_id="+Object;
				caller=new APICaller();
				response = caller.GetDataFromUrl(uri);
				data=response;
				break;
			case admin_inventory_edit:
				uri=mainURL+"admin_inventory_edit.php?emp_id="+empid+"&indent_id="+indentid;
				caller=new APICaller();
				response = caller.GetDataFromUrl(uri);
				data=response;
				break;
			case admin_inv_update:
				uri=mainURL+"admin_inv_update.php?emp_id="+empid+"&indent_id="+indentid+"&inv_id="+inv_id+"&status="+status+"&approved_qty="+approved_qty+"&shp_charges="+shp_charges+"&inward_remarks="+inward_remarks+"";
				caller=new APICaller();
				response = caller.GetDataFromUrl(uri);
				data=response;
				break;
			case stockist_inventory:
				uri=mainURL+"stockist_inventory.php?emp_id="+Object;
				caller=new APICaller();
				response = caller.GetDataFromUrl(uri);
				data=response;
				break;
			case stockist_inventory_edit:
				uri=mainURL+"stockist_inventory_edit.php?emp_id="+empid+"&indent_id="+indentid;
				caller=new APICaller();
				response = caller.GetDataFromUrl(uri);
				data=response;
				break;
			case stockist_inv_update:
				uri=mainURL+"stockist_inv_update.php?emp_id="+empid+"&indent_id="+indentid+"&inv_id="+inv_id+"&status="+status+"&shp_remarks="+inward_remarks+"&shp_date="+shp_date;
				caller=new APICaller();
				response = caller.GetDataFromUrl(uri);
				data=response;
				break;
			case EMPGPSLOC:
				uri=mainURL+"emp_gps_loc.php?emp_id="+emp_id+"&gps_lat="+gps_lat+"&gps_lang="+gps_lang+"";
				caller=new APICaller();
				response = caller.GetDataFromUrl(uri);
				data=response;
				break;
			case CLOSEDCOMP:
				uri=mainURL+"get_closed_comp_cat.php";
				caller=new APICaller();
				response = caller.GetDataFromUrl(uri);
				data=response;
				break;
		}
		try {
			Bundle extras = intent.getExtras();
			if (extras != null) {
				Bundle bundle = new Bundle();
				bundle.putString("data", data);
				Messenger messenger = (Messenger) extras.get("MESSENGER");
				Message msg = Message.obtain();
				msg.setData(bundle);
				try {
					messenger.send(msg);
				} catch (RemoteException e) {
					Log.e(LOG_TAG, "Exception sending message", e);
				}
			}
		}
		catch (Exception e) {
			Log.e(LOG_TAG, "Exception sending message", e);
		}
	}
}

