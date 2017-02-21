package com.digitalrupay.msrc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.digitalrupay.msrc.R;
import com.digitalrupay.msrc.dataModel.InventoryData;

import java.util.ArrayList;

/**
 * Created by sridhar on 2/13/2017.
 */

public class ComplaintInventoryListAdapter extends BaseAdapter {

    static Context context;
    ArrayList<InventoryData> list;

    public ComplaintInventoryListAdapter(Context playlistFragment, ArrayList<InventoryData> vList) {
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
        view = inflater.inflate(R.layout.adapter_complaint_inventorylist, parent, false);
        TextView empName = (TextView) view.findViewById(R.id.empName);
//        TextView tot_emp_inv = (TextView) view.findViewById(R.id.tot_emp_inv);
        String getName = list.get(position).getname();
        String getitem_number = list.get(position).getitem_number();
        String gettot_emp_inv = list.get(position).gettot_emp_inv();
        empName.setText(getName + " (" + getitem_number + ")");
//        tot_emp_inv.setText(gettot_emp_inv);
        return view;
    }

}