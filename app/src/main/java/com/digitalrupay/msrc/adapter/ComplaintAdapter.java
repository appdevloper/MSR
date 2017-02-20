package com.digitalrupay.msrc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.digitalrupay.msrc.R;
import com.digitalrupay.msrc.dataModel.ComplaintData;
import com.digitalrupay.msrc.dataModel.InventoryData;

import java.util.ArrayList;

/**
 * Created by sridhar on 2/10/2017.
 */

public class ComplaintAdapter extends BaseAdapter {
    static Context context;
    ArrayList<ComplaintData> list;

    public ComplaintAdapter(Context playlistFragment, ArrayList<ComplaintData> vList) {
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
        view = inflater.inflate(R.layout.adapter_complaint_list, parent, false);
        TextView empName=(TextView)view.findViewById(R.id.custName);
        TextView custPhoneadderss=(TextView)view.findViewById(R.id.custPhoneadderss);
        String getName=list.get(position).getfirst_name();
        String lastname=list.get(position).getlast_name();
        String custom_customer_no=list.get(position).getcustom_customer_no();
        String mobileno=list.get(position).getmobile_no();
        String city=list.get(position).getcity();
        empName.setText(getName+" ("+custom_customer_no+")");
        custPhoneadderss.setText(mobileno+" , "+city);
        return view;
    }
}