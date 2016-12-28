package com.bt.contactlist;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Monika on 11/11/2016.
 * custom cursor adapter
 */

public class CustomAdapter extends CursorAdapter {
    private LayoutInflater mLayoutInflater;

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View itemView = mLayoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.textView = (TextView) itemView.findViewById(R.id.contact_name);
        itemView.setTag(viewHolder);
        return itemView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder holder = (ViewHolder) view.getTag();
        final String displayName = cursor.getString(ListFragment.ContactsQuery.DISPLAY_NAME);
        holder.textView.setText(displayName);
    }

    // view holder class
    private static  class ViewHolder{
        TextView textView;
    }

    // custom adapter as there is no constructor in super class
    CustomAdapter (Context context) {
        super(context, null, 0);
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        if (getCursor() == null) {
            return 0;
        } else {
            return super.getCount();
        }
    }

    @Override
    public Object getItem(int position) {
        return getCursor().getPosition();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

   /* @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null)
        {   viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.list_item,null);
            viewHolder.textView=(TextView) convertView.findViewById(R.id.text3);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder= (ViewHolder)convertView.getTag();
        }
        viewHolder.textView.setText(contacts.get(position).getName());
        return convertView;
    }*/
}
