package com.digitalrupay.msrc.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalrupay.msrc.R;
import com.digitalrupay.msrc.dataModel.InventoryData;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by sridhar on 2/9/2017.
 */

public class InventoryListAdapter extends BaseAdapter {

    static Context context;
    ArrayList<InventoryData> list;

    public InventoryListAdapter(Context playlistFragment, ArrayList<InventoryData> vList) {
        list = vList;
        context = playlistFragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.adapter_inventory_list, parent, false);
        TextView empName=(TextView)view.findViewById(R.id.empName);
        TextView tot_emp_inv=(TextView)view.findViewById(R.id.tot_emp_inv);
        String getName=list.get(position).getname();
        String getitem_number=list.get(position).getitem_number();
        String gettot_emp_inv=list.get(position).gettot_emp_inv();
        empName.setText(getName+" ("+getitem_number+")");
        tot_emp_inv.setText(gettot_emp_inv);
        return view;
    }


}