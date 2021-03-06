package com.example.todoist;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.chip.Chip;
import com.google.android.material.tabs.TabLayout;

public class ReminderFragment extends DialogFragment implements SaveDateAndTime {
    TabLayout tabLayout;
    FrameLayout frame_layout;
    Fragment fragment;
    TimeFragment timeFragment = new TimeFragment();
    PlaceFragment placeFragment = new PlaceFragment();
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Button save, cancel;
    SaveTimeAndDateCallBack saveTimeAndDateCallBack;
    //    SavePlaceCallback savePlaceCallback;
    String date, time, place;
    public static final String KEY = "tm";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.reminder_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        save = view.findViewById(R.id.save);
        cancel = view.findViewById(R.id.cancel);
        tabLayout = view.findViewById(R.id.tabLayout);
        frame_layout = view.findViewById(R.id.frameOne);
        fragmentManager = getChildFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameOne, timeFragment);
        if (getArguments() != null) {
            Log.d("ReminderFragment", getArguments().getParcelable(KEY) + " ");
            final Bundle bundle = getArguments();
            timeFragment.setArguments(bundle);
        }

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
        timeFragment.setSaveDateAndTime(ReminderFragment.this);
//        placeFragment.setSavePlace(ReminderFragment.this);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.time));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1E88E5"));
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#000000"));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.place));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fragmentManager = getChildFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameOne, timeFragment);
                        fragmentTransaction.commit();
                        break;
                    case 1:
                        fragmentManager = getChildFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameOne, placeFragment);
                        fragmentTransaction.commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        save.setOnClickListener(v -> {
            saveTimeAndDateCallBack.onSaveTimeAndDate(date, time);
//            savePlaceCallback.savePlace(place);
            dismiss();
        });


        cancel.setOnClickListener(v -> dismiss());
    }

    void setSaveTimeAndDateCallBack(SaveTimeAndDateCallBack saveTimeAndDateCallBack) {
        this.saveTimeAndDateCallBack = saveTimeAndDateCallBack;
    }


    @Override
    public void onSaveDate(String date) {
        this.date = date;
    }

    @Override
    public void onSaveTime(String time) {
        this.time = time;
    }

/*    @Override
    public void onSavePlace(String place) {
        this.place = place;
    }*/
}