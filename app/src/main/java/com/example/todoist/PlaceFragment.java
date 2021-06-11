package com.example.todoist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;

public class PlaceFragment extends Fragment {

    EditText editLocation;
    RadioButton radioButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.place_fragment, container, false);
        radioButton = v.findViewById(R.id.radioButton);
        editLocation = v.findViewById(R.id.editLocation);
        return v;
    }
}