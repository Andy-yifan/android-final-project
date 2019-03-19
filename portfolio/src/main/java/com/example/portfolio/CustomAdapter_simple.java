package com.example.portfolio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter_simple extends ArrayAdapter<Info_holder> {
    private int resourceLayout;
    private Context mContext;
    public CustomAdapter_simple( Context context, int resource,  ArrayList<Info_holder> list) {
        super(context, resource, list);
        this.resourceLayout = resource;
        this.mContext = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Info_holder p = getItem(position);
        if(p==null){

        }

        if (p != null) {
            TextView title = (TextView) v.findViewById(R.id.ItemTitle);
            TextView text = (TextView) v.findViewById(R.id.ItemText);


            if (title != null) {
                title.setText("Business Name: "+p.getName());
            }

            if (text != null) {
                text.setText(p.getinfo());
            }
        }
        return v;

    }



}
