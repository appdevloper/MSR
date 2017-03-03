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
import com.digitalrupay.msrc.dataModel.InventoryItemsData;

import java.util.ArrayList;

/**
 * Created by sridhar on 2/11/2017.
 */

public class SpinnerInventoryAdapter extends BaseAdapter {

    Context c;
    ArrayList<InventoryItemsData> objects;

    public SpinnerInventoryAdapter(Context context, ArrayList<InventoryItemsData> objects) {
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
        InventoryItemsData cur_obj = objects.get(position);
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View row = inflater.inflate(R.layout.spinnertext, parent, false);
        TextView label = (TextView) row.findViewById(R.id.category);
        label.setText(cur_obj.getname()+"  ("+cur_obj.getitem_number()+")");
        return row;
    }
}