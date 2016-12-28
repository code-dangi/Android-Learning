package com.bt.samplerecyclerviewwithcursoradapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Monika on 12/21/2016.
 * cursor adapter that binds data from table columns to views
 */

public class CursorRecyclerViewAdapter extends RecyclerView.Adapter {
    private LayoutInflater mEmployeeListInflater;
    private Cursor mCursor;
    private boolean mIsDataValid;
    private int mRowIdColumn;

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mEmployeeName;
        private TextView mDeptName;
        //constructor
        MyViewHolder(View view) {
            super(view);
            mEmployeeName = (TextView) view.findViewById(R.id.recycler_name);
            mDeptName = (TextView) view.findViewById(R.id.recycler_d_name);
        }
    }
    CursorRecyclerViewAdapter(Context context, Cursor cursor) {
        init(cursor);
        mEmployeeListInflater = LayoutInflater.from(context);
    }


    private void init(Cursor cursor) {
        mIsDataValid = cursor != null;
        mCursor = cursor;
        mRowIdColumn = mIsDataValid ? cursor.getColumnIndexOrThrow("_id") : -1;
        hasStableIds();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!mIsDataValid) {
            throw new IllegalStateException("bind view holder should be called if cursor is present");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("cant move cursor to position "+ position);
        }
        onBindViewHolderWithCursor((MyViewHolder)holder, mCursor);
    }

    private void onBindViewHolderWithCursor(MyViewHolder holder, Cursor mCursor) {
        holder.mEmployeeName.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(
                EmployeeDetailContract.Employee.COLUMN_NAME_E_NAME)));
        holder.mDeptName.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(
                EmployeeDetailContract.Employee.COLUMN_NAME_D_NAME)));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mEmployeeListInflater.inflate(R.layout.employee_detail_row, parent, false);
        return new MyViewHolder(view);
    }

    public Cursor getCursor() {
        return mCursor;
    }

    @Override
    public int getItemCount() {
        return mIsDataValid ?  mCursor.getCount() : 0;
    }

    @Override
    public long getItemId(int position) {
        if (mIsDataValid && hasStableIds()) {
            if (mCursor.moveToPosition(position)) {
                return mCursor.getLong(mRowIdColumn);
            } else {
                return RecyclerView.NO_ID;
            }
        } else {
            return RecyclerView.NO_ID;
        }
    }
    public void changeCursor (Cursor cursor) {
        Cursor old = swap(cursor);
        if (old != null) {
            old.close();
        }
    }
    /**
     * cursor is set to new cursor and old cursor is returned bak
     * @param newCursor new cursor to be set
     * @return old cursor
     */
    private Cursor swap(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        Cursor old = mCursor;
        if (newCursor != null) {
            mRowIdColumn = newCursor.getColumnIndexOrThrow("_id");
            mIsDataValid = true;
        } else {
            mRowIdColumn = -1;
            mIsDataValid = false;
        }
        return old;
    }
    public CharSequence convertToString(Cursor cursor) {
        return cursor == null ? "" : cursor.toString();
    }
}
