package com.bt.samplerecyclerviewwithcursoradapters;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bt.samplerecyclerviewwithcursoradapters.EmployeeDetailContract.Employee;

import static android.view.inputmethod.EditorInfo.IME_ACTION_NEXT;

/**
 * Created by Monika on 12/21/2016.
 * Launching Activity
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName() ;
    private EditText mEmployeeName;
    private EditText mDepartmentName;
    private RecyclerView mEmployeeDetailRecycler;
    private EmployeeDbHelper mDbHelper;
    CursorRecyclerViewAdapter mCursorAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmployeeName = (EditText) findViewById(R.id.e_name);
        mDepartmentName = (EditText) findViewById(R.id.d_name);
        findViewById(R.id.button_save).setOnClickListener(this);
        findViewById(R.id.button_retrieve).setOnClickListener(this);
        mEmployeeDetailRecycler = (RecyclerView) findViewById(R.id.employee_recycler);
        mEmployeeDetailRecycler.setLayoutManager(new LinearLayoutManager(this));
        mDbHelper = new EmployeeDbHelper(this);
        setInputOptions();
        setEditorListener();
    }
    private class EnterKeyListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() != KeyEvent.ACTION_DOWN) {
                return false;
            }
            switch (textView.getId()) {
                case R.id.d_name:
                    if (!mEmployeeName.getText().toString().equals("")) {
                        if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            saveDetails();
                        }
                    }
                    break;
                case R.id.e_name:
                    if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        mEmployeeName.setNextFocusForwardId(R.id.d_name);
                    }
                    break;
            }
            return true;
        }
    }
    private void setEditorListener() {
        EnterKeyListener listener = new EnterKeyListener();
        mDepartmentName.setOnEditorActionListener(listener);
        mEmployeeName.setOnEditorActionListener(listener);
    }

    private void setInputOptions() {
        mEmployeeName.setImeOptions(IME_ACTION_NEXT);
        mDepartmentName.setImeOptions(IME_ACTION_NEXT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_save:
                saveDetails();
                break;
            case R.id.button_retrieve:
                retrieveDetails();
        }
    }
    private void saveDetails() {
        SQLiteDatabase db;
        ContentValues values = new ContentValues();
        // map of values to be written into database
        values.put(Employee.COLUMN_NAME_E_NAME, mEmployeeName.getText().toString());
        values.put(Employee.COLUMN_NAME_D_NAME, mDepartmentName.getText().toString());
        db = mDbHelper.getWritableDatabase();
        long rowId = db.insert(Employee.TABLE_NAME, null, values);
        mCursorAdapter.notifyDataSetChanged();
        showNotification("New row is added to table" + "with row id:  "+ rowId);
    }

    private void retrieveDetails() {
        SQLiteDatabase db;
        db = mDbHelper.getReadableDatabase();
        Cursor c = db.query(Employee.TABLE_NAME, Employee.PROJECTION_ALL, null, null, null, null, null);
        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToFirst();
                mCursorAdapter = new CursorRecyclerViewAdapter(this, c);
                mCursorAdapter.notifyDataSetChanged();
                mEmployeeDetailRecycler.setAdapter(mCursorAdapter);
                showNotification("adapter attached successfully");
            } else {
                showNotification("there no data returned by query");
            }

        }
    }
    private void showNotification (String message) {
        Snackbar.make(mEmployeeName, message, Snackbar.LENGTH_SHORT).show();
    }

}
