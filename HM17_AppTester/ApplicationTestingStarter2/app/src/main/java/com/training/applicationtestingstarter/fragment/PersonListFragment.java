package com.training.applicationtestingstarter.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;


import com.training.applicationtestingstarter.R;
import com.training.applicationtestingstarter.object.Person;
import com.training.applicationtestingstarter.util.PersonStorageUtil;

import java.util.ArrayList;
import java.util.List;

public class PersonListFragment extends Fragment implements AdapterView.OnItemClickListener {
    public static final String TAG = "PersonListFragment.TAG";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frgment_list, container, false);
    }

    public static PersonListFragment newInstance() {
        return new PersonListFragment();
    }



    public interface OnPersonSelectedListener {
        void onPersonSelected(Person _person);
    }

    private OnPersonSelectedListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnPersonSelectedListener) {
            mListener = (OnPersonSelectedListener)context;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //setEmptyText("No Data Available");
        refresh();
    }


    public void refresh() {
        ArrayList<Person> people = PersonStorageUtil.loadPeople(getActivity());
        ArrayAdapter<Person> peopleAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, people);
        ListView listview = (ListView) getView().findViewById(R.id.person_list);
        listview.setAdapter(peopleAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Person person = (Person) adapterView.getAdapter().getItem(i);
                if(mListener != null) {
                    mListener.onPersonSelected(person);
                }
            }
        });
        //listview.setListAdapter(peopleAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Person person = (Person) adapterView.getAdapter().getItem(i);
        if(mListener != null) {
            mListener.onPersonSelected(person);
        }
    }

//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//
//        Person person = (Person) l.getAdapter().getItem(position);
//        if(mListener != null) {
//            mListener.onPersonSelected(person);
//        }
//    }
}
