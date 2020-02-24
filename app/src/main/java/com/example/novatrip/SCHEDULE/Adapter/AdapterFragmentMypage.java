package com.example.novatrip.SCHEDULE.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.novatrip.SCHEDULE.UI.ScheduleFragmentMypage_first;
import com.example.novatrip.SCHEDULE.UI.ScheduleFragmentMypage_second;

public class AdapterFragmentMypage extends FragmentPagerAdapter {

    public AdapterFragmentMypage(FragmentManager fm  ) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new ScheduleFragmentMypage_second();
            case 1:
                return new ScheduleFragmentMypage_second();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
