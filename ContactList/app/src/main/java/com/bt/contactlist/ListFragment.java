package com.bt.contactlist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Monika on 11/11/2016.
 */

public class ListFragment extends Fragment {
    private CustomAdapter mAdapter;
    private onClickItemListner mItemListner;
    static final int nContacts=20;
    static final String name_list="name_list";
    static final String fragFlag="frag_indicator";
    static ArrayList<Contacts> Contact;
    private ListView mListView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof onClickItemListner )
        {
            mItemListner = (onClickItemListner) context;
        }
        else{
            throw new RuntimeException(context.toString()+" should Implement item click listener");
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Contact=new ArrayList<Contacts>(nContacts);
        if(savedInstanceState==null) {
            Contacts c;
            for (int i = 0; i < nContacts; i++) {
                c = new Contacts(i);
                Contact.add(c);
            }
        }
        else{
            Contact=  (ArrayList<Contacts>) savedInstanceState.getSerializable(name_list);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mListView = (ListView) view.findViewById(R.id.listView);

        mAdapter = new CustomAdapter(getActivity(), Contact);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mItemListner.onItemClick(Contact.get(position));
            }
        });
        return view;

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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable(name_list,Contact);
        outState.putInt(fragFlag,0);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface onClickItemListner {
        void onItemClick(Contacts contacts);
    }

}

