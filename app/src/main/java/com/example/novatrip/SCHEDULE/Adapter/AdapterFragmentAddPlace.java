package com.example.novatrip.SCHEDULE.Adapter;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.novatrip.SCHEDULE.UI.ScheduleFragmentAddPlace_all;
import com.example.novatrip.SCHEDULE.UI.ScheduleFragmentAddPlace_restaurant;
import com.example.novatrip.SCHEDULE.UI.ScheduleFragmentAddPlace_shopping;
import com.example.novatrip.SCHEDULE.UI.ScheduleFragmentAddPlace_sights;
import com.example.novatrip.SCHEDULE.UI.ScheduleFragmentPlace_intro;
import com.example.novatrip.SCHEDULE.UI.ScheduleFragmentPlace_recommendation;
import com.example.novatrip.SCHEDULE.UI.ScheduleFragmentPlace_restaurant;
import com.example.novatrip.SCHEDULE.UI.ScheduleFragmentPlace_touristAttraction;

public class AdapterFragmentAddPlace extends FragmentPagerAdapter {

    private static final String TAG = Context.class.getSimpleName();
    String unixTime ;
    int position ;
    int idx_travel_plan ;
    int idx_local;
    AdapterScheduleChoicePlace adapterScheduleChoicePlace;



    public AdapterFragmentAddPlace(FragmentManager fm , String unixTime, int position  , int idx_travel_plan , int idx_local , AdapterScheduleChoicePlace adapterScheduleChoicePlace) {
        super(fm);
        this.idx_local = idx_local ;
        this.unixTime = unixTime ;
        this.position = position ;
        this.idx_travel_plan = idx_travel_plan ;
        this.adapterScheduleChoicePlace = adapterScheduleChoicePlace;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                Log.d(TAG, "getItem: 모두");
                return new ScheduleFragmentAddPlace_all(  unixTime,   position  ,  idx_travel_plan ,  idx_local ,adapterScheduleChoicePlace);
            case 1:
                Log.d(TAG, "getItem: 명소");
                return new ScheduleFragmentAddPlace_sights(unixTime,   position  ,  idx_travel_plan ,  idx_local ,adapterScheduleChoicePlace);
            case 2:
                Log.d(TAG, "getItem: 식당");
                return new ScheduleFragmentAddPlace_restaurant(unixTime,   position  ,  idx_travel_plan ,  idx_local ,adapterScheduleChoicePlace);
            case 3:
                Log.d(TAG, "getItem: 쇼핑");
                return new ScheduleFragmentAddPlace_shopping(unixTime,   position  ,  idx_travel_plan ,  idx_local ,adapterScheduleChoicePlace);

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 4;
    }
}


