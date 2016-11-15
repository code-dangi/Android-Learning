package com.bt.contactlist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Objects;
import java.util.Arrays;

import static android.R.layout.simple_list_item_1;

/**
 * Created by Monika on 11/11/2016.
 */

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Contacts> contacts;
    static  class ViewHolder{
        TextView textView;
    }

    public CustomAdapter(Context context, ArrayList<Contacts> contacts) {
        this.context=context;
        this.contacts=contacts;

    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null)
        {   viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.list_view,null);
            viewHolder.textView=(TextView) convertView.findViewById(R.id.text3);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder= (ViewHolder)convertView.getTag();
        }

        viewHolder.textView.setText(contacts.get(position).getName());
        return convertView;
    }
}
