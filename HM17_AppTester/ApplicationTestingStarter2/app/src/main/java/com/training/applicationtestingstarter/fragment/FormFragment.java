package com.training.applicationtestingstarter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.training.applicationtestingstarter.R;
import com.training.applicationtestingstarter.object.Person;


public class FormFragment extends Fragment {
    public static final String TAG = "FormFragment.TAG";

    public static FormFragment newInstance() {
        return new FormFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    public Person getPersonFromLayout() {

        View view = getView();
        if(view == null) {
            return null;
        }


        EditText editFirst = (EditText)view.findViewById(R.id.edit_first_name);
        EditText editLast = (EditText)view.findViewById(R.id.edit_last_name);
        EditText editAge = (EditText)view.findViewById(R.id.edit_age);

        String firstName = editFirst.getText().toString();
        String lastName = editLast.getText().toString();
        String ageString = editAge.getText().toString();

        int age = -1;
        try { age = Integer.parseInt(ageString); }
        catch(Exception e) { }

        if(firstName.trim().length() == 0 ||
                lastName.trim().length() == 0 ||
                age == -1) {
            return null;
        }

        return new Person(firstName, lastName, age);
    }
}
