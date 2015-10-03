package com.earthquakemanager.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.earthquakemanager.Model.RowItem;
import com.earthquakemonitor.R;


public class QuakeAdapter extends BaseAdapter{

    List<RowItem> rowItemList;
    Context context;

    public QuakeAdapter(Context context,List<RowItem> rowItemList)
    {
        this.context=context;
        this.rowItemList=rowItemList;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.item, null);
        TextView mangitude= (TextView) convertView.findViewById(R.id.textViewMagnitude);
        TextView location= (TextView) convertView.findViewById(R.id.textViewLocation);

        RowItem rowItem = (RowItem) getItem(position);
        mangitude.setText(rowItem.getMagnitude());
        location.setText(rowItem.getLocation());


        return convertView;
    }


    @Override
    public int getCount() {
        return rowItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItemList.indexOf(getItem(position));
    }


}