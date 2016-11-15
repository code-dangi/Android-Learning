package com.bt.contactlist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Monika on 11/11/2016.
 */

public class DetailContactFragment extends Fragment {

    private View view;
    static private String text1="text1";
    static private String text2="text2";
    static final String fragFlag="frag_indicator";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view =inflater.inflate(R.layout.fragment_detail,container,false);
        if(savedInstanceState!=null){
            TextView textView1 = (TextView) view.findViewById(R.id.name);
            textView1.setText(savedInstanceState.getCharSequence(text1));
            textView1 = (TextView) view.findViewById(R.id.number);
            textView1.setText(savedInstanceState.getCharSequence(text2));
        }
        if(getArguments()!=null) {
            TextView textView1 = (TextView) view.findViewById(R.id.name);
            textView1.setText(getArguments().getString("Name"));
            textView1 = (TextView) view.findViewById(R.id.number);
            textView1.setText(getArguments().getString("Number"));
        }
        return view;
    }

    public void setTextView(String name, String number)
    {
        TextView textView1=(TextView) view.findViewById(R.id.name);
        textView1.setText(name);
        textView1.setTextSize(20);
        TextView textView2=(TextView) view.findViewById(R.id.number);
        textView2.setText(number);
        textView2.setTextSize(30);
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        TextView textView1= (TextView) view.findViewById(R.id.name);
        outState.putCharSequence(text1,textView1.getText());
        textView1=(TextView) view.findViewById(R.id.number);
        outState.putCharSequence(text2,textView1.getText());
        outState.putInt(fragFlag,1);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

