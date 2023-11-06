package com.training.applicationtestingstarter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.training.applicationtestingstarter.R;
import com.training.applicationtestingstarter.object.Person;


public class DetailsFragment extends Fragment {
    public static final String TAG = "DetailsFragment.TAG";

    private static final String ARG_PERSON = "DetailsFragment.ARG_PERSON";

    public static DetailsFragment newInstance(Person _person) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PERSON, _person);
        fragment.setArguments(args);
        return fragment;
    }

    private Person mPerson;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        View view = getView();

        if(args != null && args.containsKey(ARG_PERSON) && view != null) {

            mPerson = (Person)args.getSerializable(ARG_PERSON);

            TextView tv = (TextView)view.findViewById(R.id.text_first_name);
            tv.setText(mPerson.getFirstName());

            tv = (TextView)view.findViewById(R.id.text_last_name);
            tv.setText(mPerson.getLastName());

            tv = (TextView)view.findViewById(R.id.text_age);
            tv.setText("" + mPerson.getAge());
        }
    }

    public Person getPerson() {
        return mPerson;
    }
}
