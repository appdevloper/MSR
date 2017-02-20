package com.digitalrupay.msrc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.digitalrupay.msrc.R;
import com.digitalrupay.msrc.dataModel.AdmIninventoryData;
import com.digitalrupay.msrc.dataModel.ComplaintData;

import java.util.ArrayList;

/**
 * Created by sridhar on 2/14/2017.
 */

public class AdminListAdapter extends BaseAdapter {
    static Context context;
    ArrayList<AdmIninventoryData> list;

    public AdminListAdapter(Context playlistFragment, ArrayList<AdmIninventoryData> vList) {
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
        view = inflater.inflate(R.layout.adapter_admin_inventory_list, parent, false);
        TextView empName = (TextView) view.findViewById(R.id.admin_empName);
        TextView custPhoneadderss = (TextView) view.findViewById(R.id.admin_details);
        String getName = list.get(position).getemp_first_name();
        String lastname = list.get(position).getemp_last_name();
        String inventoryname = list.get(position).getname();
        String required_qty = list.get(position).getrequired_qty();
        empName.setText(getName + " " + lastname);
        custPhoneadderss.setText(inventoryname + "-" + required_qty);
        return view;
    }
}