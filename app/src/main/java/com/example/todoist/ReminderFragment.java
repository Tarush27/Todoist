package com.example.todoist;

import android.graphics.Color;
import android.os.Bundle;
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

import com.google.android.material.tabs.TabLayout;

public class ReminderFragment extends DialogFragment implements SaveDateAndTime {

    TabLayout tabLayout;
    FrameLayout frame_layout;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Button save, cancel;

    SaveTimeAndDateCallBack saveTimeAndDateCallBack;
    String date, time;

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
        fragment = new TimeFragment();
        fragmentManager = getChildFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameOne, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
        tabLayout.addTab(tabLayout.newTab().setText(R.string.time));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1E88E5"));
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#000000"));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.place));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new TimeFragment();
                        break;
                    case 1:
                        fragment = new PlaceFragment();
                        break;
                }
                FragmentManager fm = getChildFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameOne, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        save.setOnClickListener(v -> saveTimeAndDateCallBack.onSaveTimeAndDate(date, time));
        cancel.setOnClickListener(v -> dismiss());
    }

//    void setSaveTimeAndDateCallBack(SaveTimeAndDateCallBack saveTimeAndDateCallBack) {
//        this.saveTimeAndDateCallBack = saveTimeAndDateCallBack;
//    }

    @Override
    public void onSaveDate(String date) {
        this.date = date;
    }

    @Override
    public void onSaveTime(String time) {
        this.time = time;
    }
}