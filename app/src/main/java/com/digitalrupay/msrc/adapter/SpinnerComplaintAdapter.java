package com.digitalrupay.msrc.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.digitalrupay.msrc.R;
import com.digitalrupay.msrc.dataModel.CategoryListData;
import com.digitalrupay.msrc.dataModel.ComplaintData;

import java.util.ArrayList;

/**
 * Created by sridhar on 2/10/2017.
 */

public class SpinnerComplaintAdapter extends BaseAdapter {

    Context c;
    ArrayList<ComplaintData> objects;

    public SpinnerComplaintAdapter(Context context, ArrayList<ComplaintData> objects) {
        super();
        this.c = context;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ComplaintData cur_obj = objects.get(position);
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View row = inflater.inflate(R.layout.spinnertext, parent, false);
        TextView label = (TextView) row.findViewById(R.id.category);
        label.setText(cur_obj.getfirst_name()+"  ("+cur_obj.getcustom_customer_no()+") - "+cur_obj.getcomp_ticketno());

        return row;
    }
}