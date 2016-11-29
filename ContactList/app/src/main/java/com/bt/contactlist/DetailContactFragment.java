package com.bt.contactlist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Monika on 11/11/2016.
 * Detail fragment that shows phone number and Name from the contact
 */

public class DetailContactFragment extends Fragment {
    private static final String TAG = DetailContactFragment.class.getSimpleName();
    private View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    /**
     * either to inflate detail fragment with data from saved state bundle when screen is
     * rotated or when fragment is created newly 
     * @param inflater layout inflater
     * @param container container of this fragment
     * @param savedInstanceState in case of screen rotation its not null
     * @return view is returned
     */
    @Nullable
    @Override
    public View onCreateView
            (LayoutInflater inflater, @Nullable ViewGroup container,
             @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_detail, container, false);
        TextView textView1 = (TextView) view.findViewById(R.id.name);
        TextView textView2 = (TextView) view.findViewById(R.id.number);
        if(savedInstanceState != null){
            textView1.setText(savedInstanceState.getCharSequence(getResources()
                    .getString(R.string.name)));
            textView2.setText(savedInstanceState.getCharSequence(getResources()
                    .getString(R.string.number)));
        }
        if(getArguments() != null) {
            textView1.setText(getArguments().getString(getResources().getString(R.string.name)));
            textView2.setText(getArguments().getString(getResources().getString(R.string.number)));
        }
        return view;
    }

    /**
     * On item click, detail fragment name and number is updated by the main activity
     * @param name text in name text view
     * @param number number inn number text view
     */
    public void setTextView(String name, String number)
    {
        TextView textView = (TextView) view.findViewById(R.id.name);
        textView.setText(name);
        textView.setTextSize(getResources().getDimension(R.dimen.text_size_name));
        textView = (TextView) view.findViewById(R.id.number);
        textView.setText(number);
        textView.setTextSize(getResources().getDimension(R.dimen.text_size_number));
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        TextView textView1 = (TextView) view.findViewById(R.id.name);
        outState.putCharSequence(getResources().getString(R.string.name), textView1.getText());
        textView1 = (TextView) view.findViewById(R.id.number);
        outState.putCharSequence(getResources().getString(R.string.number),textView1.getText());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }
}

