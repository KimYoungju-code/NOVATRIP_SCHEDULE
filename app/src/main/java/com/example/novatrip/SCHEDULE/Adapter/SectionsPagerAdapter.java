package com.example.novatrip.SCHEDULE.Adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.UI.ScheduleMyPageSecondTap;
import com.example.novatrip.SCHEDULE.UI.ScheduleMyPageTravelTap;

import java.util.Locale;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    Context context;


    public SectionsPagerAdapter(Context context , FragmentManager fm) {
        super(fm);
        this.context = context;

    }


    //이동할 서브클래스드를 정의하는 곳
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ScheduleMyPageTravelTap(context);

            case 1:
                return new ScheduleMyPageSecondTap(context);
        }
        return null;
    }


    //총 스와이프 될 페이지 개수
    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    //탭의 Title을 정의할 메소드.
    @Override
    public CharSequence getPageTitle(int position) {
        //position에 값에 따라 타이틀 이름이 변경될 수 있도록.
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return String.valueOf(R.string.title_section1).toUpperCase(l);
            case 1:
                return String.valueOf(R.string.title_section2).toUpperCase(l);
        }
        return null;
    }



}
