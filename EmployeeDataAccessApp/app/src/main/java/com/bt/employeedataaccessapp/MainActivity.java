package com.bt.employeedataaccessapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import static com.bt.employeedataaccessapp.EmployeeDetailContract.Employee;
/**
 * Created by Monika on 12/19/2016.
 * Launching Activity
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mEmployeeName;
    private EditText mDepartmentName;
    private RecyclerView mEmployeeDetailRecycler;

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
    }

    @Override
    public void onClick(View view) {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Employee.CONTENT_URI;
        switch (view.getId()) {
            case R.id.button_save:
                // map of values to be written into database
                ContentValues values = new ContentValues();
                values.put(Employee.COLUMN_NAME_E_NAME, mEmployeeName.getText().toString());
                values.put(Employee.COLUMN_NAME_D_NAME, mDepartmentName.getText().toString());
                // insert the new row in db and returning the id
                uri = contentResolver.insert(uri, values);
                showNotification(IConstants.SAVE_SUCCESS+ "with returned uri "+ uri);
                break;
            case R.id.button_retrieve:
                String[] projection = {Employee._ID, Employee.COLUMN_NAME_E_NAME, Employee.COLUMN_NAME_D_NAME};
                Cursor c = contentResolver.query(uri, projection, null, null, null,null);
                if (c != null) {
                    c.moveToFirst();
                    c.close();
                }

                break;
        }

    }

    private void showNotification (String message) {
       Snackbar.make(mEmployeeName, message, Snackbar.LENGTH_SHORT).show();
    }
}
