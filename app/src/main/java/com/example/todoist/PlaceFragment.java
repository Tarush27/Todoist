package com.example.todoist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class PlaceFragment extends Fragment {

    TextView placeTextView;
//    SavePlace savePlace;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.place_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        placeTextView = view.findViewById(R.id.placeTextView);
        String apiKey = view.getContext().getString(R.string.api_name);
        Places.initialize(view.getContext(), apiKey);
        placeTextView.setOnClickListener(v -> {
            List<Place.Field> list = Collections.singletonList(Place.Field.NAME);
            Intent i = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,
                    list).build(v.getContext());
            startActivityForResult(i, 100);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Place p = Autocomplete.getPlaceFromIntent(data);
//            placeTextView.setText(p.getName());
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Status s = Autocomplete.getStatusFromIntent(data);
            Log.d("PlaceFragment", "error" + s);
        }
    }

    /*
    void setSavePlace(SavePlace savePlace) {
        this.savePlace = savePlace;
    }
    */
}