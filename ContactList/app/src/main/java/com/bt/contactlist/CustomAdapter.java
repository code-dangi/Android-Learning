package com.bt.contactlist;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Monika on 11/11/2016.
 * custom cursor adapter
 */

public class CustomAdapter extends CursorAdapter {
    private LayoutInflater mLayoutInflater;
    private Context mActivityContext;
    private int mIdColumn;
    private int mDisplayNameColumn;
    private int mThumbnailUriColumn;
    private int mLookupKeyColumn;

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View itemView = mLayoutInflater.inflate(R.layout.list_item, parent, false);
        mActivityContext = context;
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.textView = (TextView) itemView.findViewById(R.id.contact_name);
        viewHolder.contactBadge = (QuickContactBadge) itemView.findViewById(R.id.quick_badge);
        mIdColumn = cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID);
        mDisplayNameColumn = cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME);
        mThumbnailUriColumn = cursor.getColumnIndexOrThrow(ContactsContract
                .Contacts.PHOTO_THUMBNAIL_URI);
        mLookupKeyColumn = cursor.getColumnIndexOrThrow(ContactsContract.Contacts.LOOKUP_KEY);
        itemView.setTag(viewHolder);
        return itemView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Uri mLookupUri;
        final ViewHolder holder = (ViewHolder) view.getTag();
        final String displayName = cursor.getString(mDisplayNameColumn);
        // get the content URI for the contact
        mLookupUri = ContactsContract.Contacts.getLookupUri(cursor.getLong(mIdColumn),
                cursor.getString(mLookupKeyColumn));
        holder.contactBadge.assignContactUri(mLookupUri);
        // check if thumbnail uri is present or not
        String thumbnailUri = cursor.getString(mThumbnailUriColumn);
        if (thumbnailUri == null) {
            Log.d("Adapter", "bindView: no uri");
            holder.contactBadge.setImageResource(R.mipmap.ic_launcher);
        } else {
            holder.contactBadge.setImageBitmap(loadThumbNail(thumbnailUri));
        }

        holder.textView.setText(displayName);
    }
    private Bitmap loadThumbNail(String string) {
        Uri thumbUri = Uri.parse(string);
        AssetFileDescriptor afd = null;
        FileDescriptor fileDescriptor = null;
        try {
            afd = mActivityContext.getContentResolver().
                    openAssetFileDescriptor(thumbUri, "r");
            if (afd != null) {
                fileDescriptor = afd.getFileDescriptor();
            }
            if (fileDescriptor != null) {
                return BitmapFactory.decodeFileDescriptor(fileDescriptor);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (afd != null) {
                try {
                    afd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    // view holder class
    private static  class ViewHolder{
        TextView textView;
        QuickContactBadge contactBadge;
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
