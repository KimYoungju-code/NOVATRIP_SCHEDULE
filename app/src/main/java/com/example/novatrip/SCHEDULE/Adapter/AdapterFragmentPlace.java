package com.example.novatrip.SCHEDULE.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.novatrip.SCHEDULE.UI.ScheduleFragmentMypage_first;
import com.example.novatrip.SCHEDULE.UI.ScheduleFragmentMypage_second;
import com.example.novatrip.SCHEDULE.UI.ScheduleFragmentPlace_intro;
import com.example.novatrip.SCHEDULE.UI.ScheduleFragmentPlace_recommendation;
import com.example.novatrip.SCHEDULE.UI.ScheduleFragmentPlace_restaurant;
import com.example.novatrip.SCHEDULE.UI.ScheduleFragmentPlace_touristAttraction;
import com.example.novatrip.SCHEDULE.Unit.ItemTravelDetail;

public class AdapterFragmentPlace extends FragmentPagerAdapter {

    ItemTravelDetail itemTravelDetail;

    public AdapterFragmentPlace(FragmentManager fm , ItemTravelDetail itemTravelDetail ) {
        super(fm);
this.itemTravelDetail = itemTravelDetail;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                return new ScheduleFragmentPlace_intro(itemTravelDetail);
            case 1:
                return new ScheduleFragmentPlace_restaurant();
            case 2:
                return new ScheduleFragmentPlace_touristAttraction();
            case 3:
                return new ScheduleFragmentPlace_recommendation();

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 4;
    }
}


